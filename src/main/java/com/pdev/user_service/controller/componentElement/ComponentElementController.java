package com.pdev.user_service.controller.componentElement;

import com.pdev.user_service.dto.component.ComponentRequestDTO;
import com.pdev.user_service.dto.componentElement.ComponentElementRequestDTO;
import com.pdev.user_service.service.componentElement.ComponentElementService;
import com.pdev.user_service.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author @maleeshasa
 * @Date 2024/02/22
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/iam/component-element/v1")
public class ComponentElementController {

    private final ComponentElementService componentElementService;

    /**
     * This method is allowed to create or update component element
     *
     * @param componentElementRequest {@link ComponentRequestDTO} - component element request
     * @return {@link ResponseEntity<CommonResponse>} - component element created or updated response
     * @author @maleeshasa
     */
    @PostMapping(value = "/create-or-update")
    public ResponseEntity<CommonResponse> createOrUpdate(@RequestBody ComponentElementRequestDTO componentElementRequest) {
        log.info("ComponentElementController.createOrUpdate() => started.");
        return ResponseEntity.ok(componentElementService.createOrUpdate(componentElementRequest));
    }

    /**
     * This method is allowed to get all component elements
     *
     * @return {@link ResponseEntity<CommonResponse>} - all components elements
     * @author @maleeshasa
     */
    @GetMapping(value = "/get-all")
    public ResponseEntity<CommonResponse> getAll() {
        log.info("ComponentElementController.getAll() => started.");
        return ResponseEntity.ok(componentElementService.getAll());
    }
}
