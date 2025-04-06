package com.pdev.user_service.controller.accessControl;

import com.pdev.user_service.dto.accessControl.AccessControlRequestDTO;
import com.pdev.user_service.service.accessControl.AccessControlService;
import com.pdev.user_service.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/iam/access-control/v1")
public class AccessController {

    private final AccessControlService accessControlService;

    @PostMapping(value = "/create-or-update")
    public ResponseEntity<CommonResponse> createOrUpdate(@RequestBody AccessControlRequestDTO accessControlRequest) {
        return ResponseEntity.ok(accessControlService.createOrUpdate(accessControlRequest));
    }
}
