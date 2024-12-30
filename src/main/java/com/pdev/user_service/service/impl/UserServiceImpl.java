package com.pdev.user_service.service.impl;

import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.dto.user.UserResponseDTO;
import com.pdev.user_service.dto.user.userDetails.UserDetailsRequestDTO;
import com.pdev.user_service.dto.user.userDetails.UserDetailsResponseDTO;
import com.pdev.user_service.exception.RecordNotFoundException;
import com.pdev.user_service.mapper.user.UserAccountMapper;
import com.pdev.user_service.mapper.user.UserDetailsMapper;
import com.pdev.user_service.model.AuditData;
import com.pdev.user_service.model.applicationScope.ApplicationScope;
import com.pdev.user_service.model.user.User;
import com.pdev.user_service.model.user.UserHasApplicationScopeHasUserRole;
import com.pdev.user_service.repository.applicationScope.ApplicationScopeRepository;
import com.pdev.user_service.repository.user.UserHasApplicationScopeHasUserRoleRepository;
import com.pdev.user_service.repository.user.UserRepository;
import com.pdev.user_service.service.UserService;
import com.pdev.user_service.service.validation.ValidateUser;
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
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ApplicationScopeRepository applicationScopeRepository;
    private final UserHasApplicationScopeHasUserRoleRepository userHasApplicationScopeHasUserRoleRepository;

    private final UserDetailsMapper userDetailsMapper;
    private final UserAccountMapper userAccountMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ValidateUser validateUser;
    private final CommonUtil commonUtil;

    /**
     * This method is allowed to create or modify user
     *
     * @param userRequest {@link UserRequestDTO} - user request details
     * @param userType    {@link String} - user type
     * @return {@link CommonResponse} - user created or modified response
     * @author maleesahsa
     */
    @Override
    @Transactional
    public CommonResponse createOrModify(UserRequestDTO userRequest, String userType) {
        log.info("UserServiceImpl.createOrModify() => started.");
        CommonResponse commonResponse = new CommonResponse();
        String message;

        User user = userRepository.findByUserName(userRequest.getUserName());
        boolean isNewUser = (user == null);
// TODO: Non AD and AD should consider
        if (isNewUser) {
            log.info("User is new user.");
            user = new User();
            user.setEmail(userRequest.getEmail());
            user.setAccountNonLocked(Boolean.TRUE);
            user.setIsEmailVerified(Boolean.FALSE);
            user.setUserName(userRequest.getUserName());
            user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword())); // Set password only for new user
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
        userAccountMapper.mapToEntity(user, userRequest);

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
        user.getUserHasAuthorizeParties().forEach(entity -> {
            entity.setActive(Boolean.FALSE);
        });

        user.getUserHasApplicationScopeHasUserRoles().forEach(entity -> {
            entity.setActive(Boolean.FALSE);
        });
        log.info("UserServiceImpl.inActiveExistingUserData() => ended.");
    }

    /**
     * This method is allowed to get user by username
     *
     * @param userName {@link String} - user name
     * @return {@link CommonResponse} - user details response by username
     * @author maleesahsa
     */
    @Override
    public CommonResponse getByUserName(String userName) {
        log.info("UserServiceImpl.getByUserName() => started.");
        User user = userRepository.findByUserName(userName);
        if (user != null) {
            log.info("Mapping user entity to dto...");
            UserResponseDTO userResponse = userAccountMapper.mapToDTO(new UserResponseDTO(), user);

            CommonResponse commonResponse = new CommonResponse();
            commonResponse.setData(userResponse);
            commonResponse.setMessage("User is exists by username.");
            commonResponse.setStatus(HttpStatus.OK);
            log.info("UserServiceImpl.getByUserName() => ended.");
            return commonResponse;

        } else {
            log.error("User is not exists by username.");
            throw new RecordNotFoundException("User is not exists by username.");
        }
    }

    /**
     * This method is allowed to get user details uuid and access token
     *
     * @param userDetailsRequest {@link UserDetailsRequestDTO} - user details request
     * @return {@link CommonResponse} - user details response
     * @author maleesahsa
     */
    @Override
    public CommonResponse getByUserDetails(UserDetailsRequestDTO userDetailsRequest) {
        log.info("UserServiceImpl.getByUserDetails() => started.");

        // Get the current username from security context
        String username = commonUtil.getUsername();
        User user = userRepository.findByUserName(username);
        ApplicationScope applicationScope = applicationScopeRepository.findByUniqueId(userDetailsRequest.getUuid());

        log.info("Validating user...");
        validateUser.validateUser(user);

        log.info("Validating user application scope...");
        validateUser.validateUserApplicationScope(user, applicationScope);

        UserHasApplicationScopeHasUserRole applicationScopeHasUserRole =
                userHasApplicationScopeHasUserRoleRepository.findByUserAndApplicationScopeAndActiveTrue(user, applicationScope).
                        getFirst();

        log.info("Mapping user details response...");
        UserDetailsResponseDTO response = userDetailsMapper.mapToDTO(new UserDetailsResponseDTO(), user, applicationScopeHasUserRole);

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(response);
        commonResponse.setStatus(HttpStatus.OK);
        commonResponse.setMessage("User details are exists.");

        log.info("UserServiceImpl.getByUserDetails() => ended.");
        return commonResponse;
    }
}
