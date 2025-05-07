package com.pdev.user_service.service.impl.user;

import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.dto.user.UserResponseDTO;
import com.pdev.user_service.exception.RecordNotFoundException;
import com.pdev.user_service.mapper.user.UserAccountMapper;
import com.pdev.user_service.model.AuditData;
import com.pdev.user_service.model.user.User;
import com.pdev.user_service.repository.user.UserRepository;
import com.pdev.user_service.service.user.ADUserService;
import com.pdev.user_service.service.validation.CommonValidation;
import com.pdev.user_service.util.CommonResponse;
import com.pdev.user_service.util.CommonUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ADUserServiceImpl implements ADUserService {

    private final UserRepository userRepository;

    private final UserAccountMapper userAccountMapper;

    private final CommonUtil commonUtil;

    /**
     * This method is allowed to create or modify AD user
     *
     * @param userRequest {@link UserRequestDTO} - AD user request details
     * @return {@link CommonResponse} - AD user created or modified response
     * @author maleesahsa
     */
    @Transactional
    @Override
    public CommonResponse createOrModifyAD(UserRequestDTO userRequest) {
        log.info("UserServiceImpl.createOrModify() => started.");
        CommonResponse commonResponse = new CommonResponse();
        String message;

        User user = userRepository.findByUserName(userRequest.getUserName());
        boolean isNewUser = (user == null);

        if (isNewUser) {
            log.info("User is new user.");
            user = new User();
            if (CommonValidation.stringNullValidation(userRequest.getPassword())) {
                log.info("User password is null.");
                commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED);
                commonResponse.setMessage("User password is not exists.");
                return commonResponse;
            }
            user.setAuditData(new AuditData(LocalDateTime.now(), commonUtil.getUsername()));
            message = "User created successfully.";

        } else {
            log.info("User is existing user.");
            user.getAuditData().setUpdatedBy(commonUtil.getUsername());
            user.getAuditData().setUpdatedOn(LocalDateTime.now());
            message = "User updated successfully.";

            // in-active existing user data
            inActiveExistingUserData(user);
        }

        // Map user entity from request dto
        log.info("Mapping user entity...");
        userAccountMapper.mapToADEntity(user, userRequest);

        try {
            log.info("Saving or updating user...");
            // Save or update the user
            User savedUser = userRepository.save(user);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setMessage(message);
            log.info("Constructing saved user response...");
            commonResponse.setData(userAccountMapper.mapToDTO(new UserResponseDTO(), savedUser));

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

    /**
     * This method is allowed to in-active existing user data
     *
     * @param user {@link User} - existing user
     * @author maleesahsa
     */
    private void inActiveExistingUserData(User user) {
        log.info("UserServiceImpl.inActiveExistingUserData() => started.");
        user.getUserHasAuthorizeParties().forEach(entity -> entity.setActive(Boolean.FALSE));

        user.getUserHasApplicationScopeHasUserRoles().forEach(entity -> entity.setActive(Boolean.FALSE));
        log.info("UserServiceImpl.inActiveExistingUserData() => ended.");
    }
}
