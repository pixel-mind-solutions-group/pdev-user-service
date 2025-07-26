package com.pdev.user_service.service.auth;

import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.util.CommonResponse;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
public interface AuthService {

    /**
     * This method is allowed to authenticate user by username and password and received the access and refresh token
     *
     * @param userRequest {@link UserRequestDTO} - user request dto
     * @return {@link CommonResponse} - authenticated user token response
     * @author maleeshasa
     */
    CommonResponse authenticateUser(UserRequestDTO userRequest);

    CommonResponse authenticateExternalUser(UserRequestDTO userRequest);
}
