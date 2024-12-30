package com.pdev.user_service.service.rest;

import com.pdev.user_service.dto.auth.AuthResponseDTO;
import com.pdev.user_service.dto.user.UserRequestDTO;

import java.util.Map;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
public interface KeyCloakClientService {

    /**
     * This method is allowed to authenticate user by username and password and received the access and refresh token
     *
     * @param userRequest {@link UserRequestDTO} - user request dto
     * @return {@link AuthResponseDTO} - authenticated user token response
     * @author maleeshasa
     */
    AuthResponseDTO authenticateUser(UserRequestDTO userRequest);

    /**
     * Validates a JWT token by calling the Keycloak service. This method sends the token to
     * a remote service for validation and processes the response to extract relevant data
     * if the token is valid.
     *
     * @param authHeader {@link String} - the JWT token with Bearer to be validated
     * @return {@link Map<String, Object>} - a map containing the validation details, such as user information, roles, and permissions
     * @author maleeshasa
     */
    Map<String, Object> validateToken(String authHeader);
}
