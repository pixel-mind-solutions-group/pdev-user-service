package com.pdev.user_service.controller.auth;

import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.service.auth.AuthService;
import com.pdev.user_service.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/iam/auth/user/v1")
public class AuthController {

    private final AuthService authService;

    /**
     * This method is allowed to authenticate user by username and password and received the access and refresh token
     *
     * @param userRequest {@link UserRequestDTO} - user request dto
     * @return {@link ResponseEntity<CommonResponse>} - authenticated user token response
     * @author maleeshasa
     */
    @PostMapping(value = "/token")
    public ResponseEntity<CommonResponse> authenticateUser(@RequestBody UserRequestDTO userRequest) {
        log.info("AuthController.authenticate() => started.");
        return ResponseEntity.ok(authService.authenticateUser(userRequest));
    }

    @PostMapping(value = "/external-token")
    public ResponseEntity<CommonResponse> authenticateExternalUser(@RequestBody UserRequestDTO userRequest) {
        log.info("AuthController.authenticate() => started.");
        return ResponseEntity.ok(authService.authenticateExternalUser(userRequest));
    }
}
