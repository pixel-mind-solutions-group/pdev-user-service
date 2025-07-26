package com.pdev.user_service.service.impl.user.external.pixelHR;

import com.pdev.user_service.builder.OneTimePasswordBuilder;
import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.dto.user.UserResponseDTO;
import com.pdev.user_service.dto.user.userDetails.UserDetailsRequestDTO;
import com.pdev.user_service.exception.RecordNotFoundException;
import com.pdev.user_service.mapper.mfa.MFARegistryMapper;
import com.pdev.user_service.mapper.user.external.PixelHR.PixelHRUserAccountMapper;
import com.pdev.user_service.model.AuditData;
import com.pdev.user_service.model.user.external.pixelHR.PixelHRUser;
import com.pdev.user_service.repository.user.external.pixelHR.PixelHRUserRepository;
import com.pdev.user_service.service.mfa.MFARegistryService;
import com.pdev.user_service.service.user.external.pixelHR.PixelHRUserService;
import com.pdev.user_service.util.CommonResponse;
import com.pdev.user_service.util.CommonUtil;
import feign.Response;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class PixelHRUserServiceImpl implements PixelHRUserService {

    private final MFARegistryMapper mfaRegistryMapper;
    private final PixelHRUserAccountMapper pixelHRUserAccountMapper;
    private final PixelHRUserRepository pixelHRUserRepository;
    private final MFARegistryService mfaRegistryService;
    private final CommonUtil commonUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final OneTimePasswordBuilder oneTimePasswordBuilder;

    @Override
    public CommonResponse createOrModify(UserRequestDTO userRequest) {
        CommonResponse commonResponse = new CommonResponse();
        String message;

        PixelHRUser user = pixelHRUserRepository.findByUserName(userRequest.getUserName());
        boolean isNewUser = (user == null);

        if (isNewUser) {
            log.info("User is new user.");
            user = new PixelHRUser();
            user.setPassword(bCryptPasswordEncoder.encode(oneTimePasswordBuilder.generatePassword()));
            user.setAuditData(new AuditData(LocalDateTime.now(), commonUtil.getUsername()));
            message = "User created successfully.";

        } else {
            log.info("User is existing user.");
            user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
            user.getAuditData().setUpdatedBy(commonUtil.getUsername());
            user.getAuditData().setUpdatedOn(LocalDateTime.now());
            message = "User updated successfully.";

            // in-active existing user data
            inActiveExistingUserData(user);
        }

        // Map user entity from request dto
        log.info("Mapping user entity...");
        pixelHRUserAccountMapper.mapToEntity(user, userRequest);

        try {
            log.info("Saving or updating user...");
            // Save or update the user
            PixelHRUser savedUser = pixelHRUserRepository.save(user);

            // Save mfa registry
            mfaRegistryService.saveMFARegistry(userRequest);

            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setMessage(message);
            log.info("Constructing saved user response...");
            commonResponse.setData(pixelHRUserAccountMapper.mapToDTO(new UserResponseDTO(), savedUser));

        } catch (RecordNotFoundException ex) {
            // Handle record not found exceptions
            log.error("error {}", ex.getMessage());
            commonResponse.setStatus(HttpStatus.BAD_REQUEST);
            commonResponse.setMessage(ex.getMessage());
            commonResponse.setData(null);

        } catch (Exception ex) {
            // Handle any other unexpected exceptions
            log.error("Unexpected error: {}", ex.getMessage());
            commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            commonResponse.setMessage("An unexpected error occurred.");
            commonResponse.setData(null);
        }
        log.info("UserServiceImpl.createOrModify() => ended.");
        return commonResponse;
    }

    private void inActiveExistingUserData(PixelHRUser user) {
        log.info("PixelHRUserServiceImpl.inActiveExistingUserData() => started.");
        user.getUserHasAuthorizeParties().forEach(entity -> entity.setActive(Boolean.FALSE));
        user.getUserHasApplicationScopeHasUserRoles().forEach(entity -> entity.setActive(Boolean.FALSE));
        log.info("PixelHRUserServiceImpl.inActiveExistingUserData() => ended.");
    }

    @Override
    public CommonResponse resetPassword(@RequestBody UserRequestDTO userRequest) {

        return null;
    }
}
