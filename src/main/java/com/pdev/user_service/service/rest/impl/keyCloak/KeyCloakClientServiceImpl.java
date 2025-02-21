package com.pdev.user_service.service.rest.impl.keyCloak;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdev.user_service.client.KeyCloakServiceClient;
import com.pdev.user_service.dto.auth.AuthResponseDTO;
import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.exception.BaseException;
import com.pdev.user_service.exception.FeignCustomException;
import com.pdev.user_service.exception.RecordNotFoundException;
import com.pdev.user_service.exception.UnauthorizedException;
import com.pdev.user_service.service.rest.keyCloak.KeyCloakClientService;
import com.pdev.user_service.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KeyCloakClientServiceImpl implements KeyCloakClientService {

    private final KeyCloakServiceClient keyCloakServiceClient;
    private final ObjectMapper objectMapper;

    /**
     * This method is allowed to authenticate user by username and password and received the access and refresh token
     *
     * @param userRequest {@link UserRequestDTO} - user request dto
     * @return {@link AuthResponseDTO} - authenticated user token response
     * @author maleeshasa
     */
    @Override
    public AuthResponseDTO authenticateUser(UserRequestDTO userRequest) {
        log.info("KeyCloakClientServiceImpl.authenticateUser() => started.");
        try {
            log.info("Calling keycloak service to authenticate user by username and password and received the access and refresh token...");
            ResponseEntity<CommonResponse> response = keyCloakServiceClient.authenticateUser(userRequest);
            if (response.getStatusCode().equals(HttpStatus.OK) && Objects.requireNonNull(response.getBody()).getData() != null &&
                    Objects.requireNonNull(response.getBody()).getStatus().equals(HttpStatus.ACCEPTED)
            ) {
                log.info("User is authenticated and received token.");
                return objectMapper.convertValue(response.getBody().getData(), AuthResponseDTO.class);

            } else {
                log.error("Error while fetching user token response.");
                throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error while fetching user token response.");
            }

        } catch (BaseException e) {
            log.error(e.getErrorDescription());
            throw new BaseException(e.getErrorCode(), e.getErrorDescription());

        } catch (RecordNotFoundException e) {
            log.error(e.getMessage());
            throw new BaseException(HttpStatus.NOT_FOUND.value(), e.getMessage());

        } catch (FeignCustomException e) {
            log.error("Error occurred while calling keycloak service to fetch user by username and password. Error: {}", e.getMessage());
            throw new BaseException(500, "Error occurred while calling keycloak service to fetch user by username and password. Error: " + e.getMessage());
        }
    }

    /**
     * Validates a JWT token by calling the Keycloak service. This method sends the token to
     * a remote service for validation and processes the response to extract relevant data
     * if the token is valid.
     *
     * @param token {@link String} - the JWT token to be validated
     * @return {@link Map<String, Object>} - a map containing the validation details, such as user information, roles, and permissions
     * @author maleeshasa
     */
    @Override
    public Map<String, Object> validateToken(String token) {
        log.info("KeyCloakClientServiceImpl.validateToken() => started.");
        try {
            log.info("Calling keycloak service to validate user token...");
            ResponseEntity<CommonResponse> response = keyCloakServiceClient.validateToken(token);
            if (response.getStatusCode().equals(HttpStatus.OK) &&
                    Objects.requireNonNull(response.getBody()).getData() != null &&
                    response.getBody().getStatus().equals(HttpStatus.ACCEPTED)) {
                log.info("Token is validated.");

                return objectMapper.convertValue(response.getBody().getData(), new TypeReference<>() {
                });

            } else {
                log.error("Unauthorized user.");
                throw new UnauthorizedException("Unauthorized user.");
            }

        } catch (UnauthorizedException e) {
            log.error("Unauthorized user. Error: {}", e.getMessage());
            throw new UnauthorizedException(e.getMessage());

        } catch (FeignCustomException e) {
            log.error("Error occurred while calling user service to validate token. Error: {}", e.getMessage());
            throw new BaseException(500, "Error occurred while calling user service to validate token. Error: " + e.getMessage());
        }
    }
}
