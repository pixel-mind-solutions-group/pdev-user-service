package com.pdev.user_service.service.impl;

import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.dto.user.UserResponseDTO;
import com.pdev.user_service.exception.RecordNotFoundException;
import com.pdev.user_service.mapper.user.UserAccountMapper;
import com.pdev.user_service.model.AuditData;
import com.pdev.user_service.model.user.User;
import com.pdev.user_service.repository.user.UserRepository;
import com.pdev.user_service.service.NonADUserService;
import com.pdev.user_service.util.CommonResponse;
import com.pdev.user_service.util.CommonUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Service(value = "non_active_directory_user")
@Slf4j
@RequiredArgsConstructor
public class NonADUserServiceImpl implements NonADUserService {

    private final UserRepository userRepository;

    private final UserAccountMapper userAccountMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CommonUtil commonUtil;

    /**
     * This method is allowed to create or modify Non AD user
     *
     * @param userRequest {@link UserRequestDTO} - Non AD user request details
     * @return {@link CommonResponse} - Non AD user created or modified response
     * @author maleesahsa
     */
    @Transactional
    @Override
    public CommonResponse createNonAD(UserRequestDTO userRequest) {
        log.info("UserServiceImpl.createNonAD() => started.");
        CommonResponse commonResponse = new CommonResponse();

        User user = userRepository.findByUserName(userRequest.getUserName());
        boolean isNewUser = (user == null);

        if (isNewUser) {
            log.info("User is new user.");
            user = new User();
            user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword())); // Set password only for new user
            user.setAuditData(new AuditData(LocalDateTime.now(), commonUtil.getUsername()));

            // Map user entity from request dto
            log.info("Mapping user entity...");
            userAccountMapper.mapToNonADEntity(user, userRequest);

            try {
                log.info("Saving or updating user...");
                // Save or update the user
                User savedUser = userRepository.save(user);
                commonResponse.setStatus(HttpStatus.OK);
                commonResponse.setMessage("User created successfully.");
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
            return commonResponse;

        } else {
            log.info("User is existing user.");
            return new CommonResponse(HttpStatus.BAD_REQUEST, "User already exists.", null);
        }
    }

    /**
     * This method is allowed to in-active existing user data
     *
     * @param user {@link User} - existing user
     * @author maleesahsa
     */
    private void inActiveExistingUserData(User user) {
        log.info("UserServiceImpl.inActiveExistingUserData() => started.");
        user.getUserHasAuthorizeParties().forEach(entity -> {
            entity.setActive(Boolean.FALSE);
        });

        user.getUserHasApplicationScopeHasUserRoles().forEach(entity -> {
            entity.setActive(Boolean.FALSE);
        });
        log.info("UserServiceImpl.inActiveExistingUserData() => ended.");
    }
}
