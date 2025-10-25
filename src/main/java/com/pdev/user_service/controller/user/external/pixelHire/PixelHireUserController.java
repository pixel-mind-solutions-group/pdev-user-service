package com.pdev.user_service.controller.user.external.pixelHire;

import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.service.user.external.pixelHire.PixelHireNonADUserService;
import com.pdev.user_service.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author maleeshasa
 * @Date 2025/10/25
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/iam/user/pixel-hire/v1")
public class PixelHireUserController {

    private final PixelHireNonADUserService pixelHireNonADUserService;

    /**
     * This method is allowed to create or modify pixel hire Non AD user
     *
     * @param userRequest {@link UserRequestDTO} - user request details
     * @return {@link ResponseEntity<CommonResponse>} - user created or modified response
     * @author maleesahsa
     */
    @PostMapping(value = "/non-ad/create")
    public ResponseEntity<CommonResponse> createNonAD(@RequestBody UserRequestDTO userRequest) {
        log.info("UserController.createNonAD() => started.");
        return ResponseEntity.ok(pixelHireNonADUserService.createNonAD(userRequest));
    }

    /**
     * This method is allowed to send verification email to pixel hire non AD user
     *
     * @param username {@link String} - username of the user
     * @return {@link ResponseEntity<CommonResponse>} - email sent response
     * @author @maleesahsa
     */
    @PostMapping(value = "/send-verification-email")
    public ResponseEntity<CommonResponse> sendVerificationEmail(@RequestParam(value = "username") String username) {
        log.info("UserController.sendVerificationEmail() => started.");
        return ResponseEntity.ok(pixelHireNonADUserService.sendVerificationEmail(username));
    }

    /**
     * This method is allowed to verify email of pixel hire non AD user
     *
     * @param userId {@link Integer} - user id
     * @param verify {@link Boolean} - verify or unverify email
     * @return {@link ResponseEntity<CommonResponse>} - email verified or unverified response
     * @author @maleesahsa
     */
    @PostMapping(value = "/verify-email")
    public ResponseEntity<CommonResponse> verifyEmail(@RequestParam(value = "uid") Integer userId,
                                                      @RequestParam(value = "verify") Boolean verify) {
        log.info("PixelHireUserController.verifyEmail() => started.");
        return ResponseEntity.ok(pixelHireNonADUserService.verifyEmail(userId, verify));
    }
}
