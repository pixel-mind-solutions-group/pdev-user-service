package com.pdev.user_service.controller.mfa;

import com.pdev.user_service.dto.user.userDetails.UserDetailsRequestDTO;
import com.pdev.user_service.service.mfa.MFARegistryService;
import com.pdev.user_service.util.CommonResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/iam/user/mfa/v1")
public class MFARegistryController {

    private final MFARegistryService mfaRegistryService;

    @GetMapping(value = "/mfa-status")
    public ResponseEntity<CommonResponse> mfaStatus(@RequestBody UserDetailsRequestDTO userDetailsRequest) {
        return ResponseEntity.ok(mfaRegistryService.mfaStatus(userDetailsRequest));
    }
}
