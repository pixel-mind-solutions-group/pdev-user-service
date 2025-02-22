package com.pdev.user_service.controller.module;

import com.pdev.user_service.dto.module.ModuleRequestDTO;
import com.pdev.user_service.service.module.ModuleService;
import com.pdev.user_service.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author @maleeshasa
 * @Date 2025/02/22
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/iam/module/v1")
public class ModuleController {

    private final ModuleService moduleService;

    /**
     * This method is allowed to create or update module
     *
     * @param moduleRequest {@link ModuleRequestDTO} - module request
     * @return {@link ResponseEntity<CommonResponse>} - module created or updated response
     * @author @maleeshasa
     */
    @PostMapping(value = "/create-or-update")
    public ResponseEntity<CommonResponse> createOrUpdate(@RequestBody ModuleRequestDTO moduleRequest) {
        log.info("ModuleController.createOrUpdate() => started.");
        return ResponseEntity.ok(moduleService.createOrUpdate(moduleRequest));
    }

    /**
     * This method is allowed to get all modules
     *
     * @return {@link ResponseEntity<CommonResponse>} - all modules
     * @author @maleeshasa
     */
    @GetMapping(value = "/get-all")
    public ResponseEntity<CommonResponse> getAll() {
        log.info("ModuleController.getAll() => started.");
        return ResponseEntity.ok(moduleService.getAll());
    }
}
