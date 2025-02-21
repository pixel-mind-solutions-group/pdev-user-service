package com.pdev.user_service.controller.applicationScope;

import com.pdev.user_service.dto.applicationScope.ApplicationScopeRequestDTO;
import com.pdev.user_service.service.applicationScope.ApplicationScopeService;
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
 * @Date 2025/02/21
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/iam/application-scope/v1")
public class ApplicationScopeController {

    private final ApplicationScopeService applicationScopeService;

    /**
     * This method is allowed to create or update application scope
     *
     * @param applicationScopeRequest {@link ApplicationScopeRequestDTO} - application scope request
     * @return {@link ResponseEntity<CommonResponse>} - created or updated response
     * @author @maleeshasa
     */
    @PostMapping(value = "/create-or-update")
    public ResponseEntity<CommonResponse> createOrUpdateApplicationScope(@RequestBody ApplicationScopeRequestDTO applicationScopeRequest) {
        log.info("ApplicationScopeController.createOrUpdateApplicationScope() => started.");
        return ResponseEntity.ok(applicationScopeService.createOrUpdateApplicationScope(applicationScopeRequest));
    }
}
