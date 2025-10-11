package com.pdev.user_service.service.impl.user.internal;

import com.pdev.user_service.dto.candidate.CandidateDTO;
import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.dto.user.UserResponseDTO;
import com.pdev.user_service.enums.CommonStatus;
import com.pdev.user_service.event.UserRegisteredPublisher;
import com.pdev.user_service.exception.RecordNotFoundException;
import com.pdev.user_service.mapper.user.UserAccountMapper;
import com.pdev.user_service.model.AuditData;
import com.pdev.user_service.model.user.internal.User;
import com.pdev.user_service.repository.user.UserRepository;
import com.pdev.user_service.service.rest.candidate.CandidateClientService;
import com.pdev.user_service.service.user.internal.NonADUserService;
import com.pdev.user_service.service.validation.user.ValidateUser;
import com.pdev.user_service.util.CommonResponse;
import com.pdev.user_service.util.CommonUtil;
import com.pdev.user_service.builder.email.EmailRequestBuilder;
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

    private final UserRegisteredPublisher userRegisteredPublisher;
    private final CandidateClientService candidateClientService;
    private final ValidateUser validateUser;
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

        // Validate non ad user creation
        validateUser.validateNonAdUserCreate(userRequest);

        User user = new User();
        User savedUser = new User();
        user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword())); // Set password only for new user
        user.setAuditData(new AuditData(LocalDateTime.now(), commonUtil.getUsername()));

        // Map user entity from request dto
        log.info("Mapping user entity...");
        userAccountMapper.mapToNonADEntity(user, userRequest);

        try {
            log.info("Saving or updating user...");
            // Save or update the user
            savedUser = userRepository.save(user);

            // Create a candidate to respective user
            CandidateDTO candidateRequest = new CandidateDTO();
            candidateRequest.setIsVerify(Boolean.FALSE);
            candidateRequest.setCommonStatus(CommonStatus.ACTIVE.getValue());
            candidateRequest.setIdUserAccount(savedUser.getId());
            candidateClientService.saveCandidate(candidateRequest);

            // Publish user registered event
            log.info("Publishing user registered event...");
            userRegisteredPublisher.publishUserRegisteredEvent(
                    EmailRequestBuilder.getEmailEventRequest(userRequest)
            );

            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setMessage("User created successfully.");
            commonResponse.setData(userAccountMapper.mapToDTO(new UserResponseDTO(), savedUser));

        } catch (RecordNotFoundException ex) {
            // Handle record not found exceptions
            log.error("error {}", ex.getMessage());
            commonResponse.setStatus(HttpStatus.BAD_REQUEST);
            commonResponse.setMessage(ex.getMessage());
            commonResponse.setData(null);
            deleteUser(savedUser);

        } catch (Exception ex) {
            // Handle any other unexpected exceptions
            log.error("Unexpected error: {}", ex.getMessage());
            commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            commonResponse.setMessage("An unexpected error occurred.");
            commonResponse.setData(null);
            deleteUser(savedUser);
        }
        return commonResponse;
    }

    private void deleteUser(User user) {
        log.info("UserServiceImpl.deleteUser() => started.");
        userRepository.delete(user);
        log.info("UserServiceImpl.deleteUser() => ended.");
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
