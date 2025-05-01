package com.pdev.user_service.service.impl.user;

import com.pdev.user_service.controller.response.PageResponse;
import com.pdev.user_service.dto.user.UserResponseDTO;
import com.pdev.user_service.dto.user.userDetails.UserDetailsRequestDTO;
import com.pdev.user_service.dto.user.userDetails.UserDetailsResponseDTO;
import com.pdev.user_service.exception.RecordNotFoundException;
import com.pdev.user_service.mapper.user.UserAccountMapper;
import com.pdev.user_service.mapper.user.UserDetailsMapper;
import com.pdev.user_service.model.applicationScope.ApplicationScope;
import com.pdev.user_service.model.user.User;
import com.pdev.user_service.model.user.UserHasApplicationScopeHasUserRole;
import com.pdev.user_service.repository.applicationScope.ApplicationScopeRepository;
import com.pdev.user_service.repository.user.UserHasApplicationScopeHasUserRoleRepository;
import com.pdev.user_service.repository.user.UserRepository;
import com.pdev.user_service.service.user.UserService;
import com.pdev.user_service.service.validation.user.ValidateUser;
import com.pdev.user_service.util.CommonResponse;
import com.pdev.user_service.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

    private final ValidateUser validateUser;
    private final CommonUtil commonUtil;

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

    @Override
    public CommonResponse getAllWithPage(PageRequest of) {
        Page<User> users = userRepository.findAll(of);
        if (!users.isEmpty()) {
            PageResponse pageResponse = PageResponse.builder()
                    .currentPage(users.getNumber())
                    .totalPages(users.getTotalPages())
                    .totalElements(users.getTotalElements())
                    .dataList(userAccountMapper.mapToLazyResponseList(users.getContent())).build();

            return new CommonResponse(
                    HttpStatus.OK, "Users are exists.", pageResponse
            );
        } else {
            return new CommonResponse(
                    HttpStatus.NO_CONTENT, "Users not found.", null
            );
        }
    }
}
