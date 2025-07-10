package com.pdev.user_service.service.impl.auth;

import com.pdev.user_service.constant.CommonConstants;
import com.pdev.user_service.dto.auth.AuthResponseDTO;
import com.pdev.user_service.dto.user.UserLoginRegistryRequestDTO;
import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.model.applicationScope.ApplicationScope;
import com.pdev.user_service.model.user.internal.User;
import com.pdev.user_service.repository.applicationScope.ApplicationScopeRepository;
import com.pdev.user_service.repository.user.UserLoginRegistryRepository;
import com.pdev.user_service.repository.user.UserRepository;
import com.pdev.user_service.service.auth.AuthService;
import com.pdev.user_service.service.rest.keyCloak.KeyCloakClientService;
import com.pdev.user_service.service.user.loginRegistry.UserLoginRegistryService;
import com.pdev.user_service.service.validation.user.ValidateUser;
import com.pdev.user_service.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final UserLoginRegistryRepository loginRegistryRepository;
    private final UserRepository userRepository;
    private final ApplicationScopeRepository applicationScopeRepository;

    private final UserLoginRegistryService userLoginRegistryService;
    private final KeyCloakClientService keyCloakClientService;
    private final ValidateUser validateUser;

    /**
     * This method is allowed to authenticate user by username and password and received the access and refresh token
     *
     * @param userRequest {@link UserRequestDTO} - user request dto
     * @return {@link CommonResponse} - authenticated user token response
     * @author maleeshasa
     */
    @Override
    public CommonResponse authenticateUser(UserRequestDTO userRequest) {
        log.info("AuthServiceImpl.authenticateUser() => started.");
        CommonResponse commonResponse = new CommonResponse();

        User user = userRepository.findByUserName(userRequest.getUserName());
        ApplicationScope applicationScope = applicationScopeRepository.findByUniqueId(userRequest.getUuid());

        log.info("Validating user...");
        validateUser.validateUser(user);

        log.info("Validating user application scope...");
        validateUser.validateUserApplicationScope(user, applicationScope);

        // calling key cloak service to get the token
        log.info("Calling key cloak service to get user token...");
        AuthResponseDTO response = keyCloakClientService.authenticateUser(userRequest);

        // save login registry
        UserLoginRegistryRequestDTO dto = new UserLoginRegistryRequestDTO();
        dto.setUser(user);
        dto.setIsLogin(CommonConstants.LOGIN);
        dto.setApplicationScope(applicationScope);
        userLoginRegistryService.saveLoginRegistry(dto);

        commonResponse.setMessage("Authentication accepted.");
        commonResponse.setData(response);
        commonResponse.setStatus(HttpStatus.ACCEPTED);
        log.info("AuthServiceImpl.authenticateUser() => ended.");
        return commonResponse;
    }
}
