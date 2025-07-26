package com.pdev.user_service.controller.user.external.pixelHR;

import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.service.user.external.pixelHR.PixelHRUserService;
import com.pdev.user_service.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/iam/user/pixel-hr/v1")
public class PixelHRUserController {

    private final PixelHRUserService pixelHRUserService;

    //@PreAuthorize("hasRole(T(com.pdev.user_service.constant.UserRoles).ADMIN) and hasAuthority(T(com.pdev.user_service.constant.RolePermissionsConstants).PERMISSION_USER_AUTH_SERVICE)")
    @PostMapping(value = "/create-or-modify")
    public ResponseEntity<CommonResponse> createOrModify(@RequestBody UserRequestDTO userRequest) {
        log.info("UserController.createOrModify() => started.");
        return ResponseEntity.ok(pixelHRUserService.createOrModify(userRequest));
    }

    @PostMapping(value = "/reset-password")
    public ResponseEntity<CommonResponse> resetPassword(@RequestBody UserRequestDTO userRequest) {
        return ResponseEntity.ok(pixelHRUserService.resetPassword(userRequest));
    }
}
