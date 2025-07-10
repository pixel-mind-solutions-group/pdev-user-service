package com.pdev.user_service.controller.user;

import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.dto.user.userDetails.UserDetailsRequestDTO;
import com.pdev.user_service.service.user.internal.ADUserService;
import com.pdev.user_service.service.user.internal.NonADUserService;
import com.pdev.user_service.service.user.internal.UserService;
import com.pdev.user_service.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/iam/user/v1")
public class UserController {

    private final UserService userService;
    private final ADUserService adUserService;
    private final NonADUserService nonADUserService;

    /**
     * This method is allowed to create or modify AD user
     *
     * @param userRequest {@link UserRequestDTO} - user request details
     * @return {@link ResponseEntity<CommonResponse>} - user created or modified response
     * @author maleesahsa
     */
    //@PreAuthorize("hasRole(T(com.pdev.user_service.constant.UserRoles).ADMIN) and hasAuthority(T(com.pdev.user_service.constant.RolePermissionsConstants).PERMISSION_USER_AUTH_SERVICE)")
    @PostMapping(value = "/ad/create-or-modify")
    public ResponseEntity<CommonResponse> createOrModifyAD(@RequestBody UserRequestDTO userRequest) {
        log.info("UserController.createOrModify() => started.");
        return ResponseEntity.ok(adUserService.createOrModifyAD(userRequest));
    }

    /**
     * This method is allowed to create or modify Non AD user
     *
     * @param userRequest {@link UserRequestDTO} - user request details
     * @return {@link ResponseEntity<CommonResponse>} - user created or modified response
     * @author maleesahsa
     */
    @PostMapping(value = "/non-ad/create")
    public ResponseEntity<CommonResponse> createNonAD(@RequestBody UserRequestDTO userRequest) {
        log.info("UserController.createNonAD() => started.");
        return ResponseEntity.ok(nonADUserService.createNonAD(userRequest));
    }

    /**
     * This method is allowed to get user by username
     *
     * @param userName {@link String} - user name
     * @return {@link ResponseEntity<CommonResponse>} - user details response by username
     * @author maleesahsa
     */
    @GetMapping(value = "/get-by-username/{userName}")
    public ResponseEntity<CommonResponse> getByUserName(@PathVariable String userName) {
        log.info("UserController.getByUserName() => started.");
        return ResponseEntity.ok(userService.getByUserName(userName));
    }

    /**
     * This method is allowed to get user details uuid and access token
     *
     * @param userDetailsRequest {@link UserDetailsRequestDTO} - user details request
     * @return {@link ResponseEntity<CommonResponse>} - user details response
     * @author maleesahsa
     */
    @PreAuthorize("hasAuthority(T(com.pdev.user_service.constant.RolePermissionsConstants).PERMISSION_USER_AUTH_SERVICE)")
    @PostMapping(value = "/get-user-details")
    public ResponseEntity<CommonResponse> getByUserDetails(@RequestBody UserDetailsRequestDTO userDetailsRequest) {
        log.info("UserController.getByUserDetails() => started.");
        return ResponseEntity.ok(userService.getByUserDetails(userDetailsRequest));
    }

    // reset password

    // reset email

    @GetMapping(value = "/get-all-page")
    public ResponseEntity<CommonResponse> getAllWithPage(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("UserController.getAllWithPage() => started.");
        return ResponseEntity.ok(userService.getAllWithPage(PageRequest.of(page, size)));
    }

    @GetMapping(value = "/get-by-id")
    public ResponseEntity<CommonResponse> getById(@RequestParam(value = "userId") Integer userId) {
        log.info("UserController.getById() => started.");
        return ResponseEntity.ok(userService.getById(userId));
    }
}
