package com.pdev.user_service.controller.component;

import com.pdev.user_service.dto.component.ComponentRequestDTO;
import com.pdev.user_service.service.component.ComponentService;
import com.pdev.user_service.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author @maleeshasa
 * @Date 2024/02/22
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/iam/component/v1")
public class ComponentController {

    private final ComponentService componentService;

    /**
     * This method is allowed to create or update component
     *
     * @param componentRequest {@link ComponentRequestDTO} - component request
     * @return {@link ResponseEntity<CommonResponse>} - component created or updated response
     * @author @maleeshasa
     */
    @PostMapping(value = "/create-or-update")
    public ResponseEntity<CommonResponse> createOrUpdateComponent(@RequestBody ComponentRequestDTO componentRequest) {
        log.info("ComponentController.createOrUpdateComponent() => started.");
        return ResponseEntity.ok(componentService.createOrUpdate(componentRequest));
    }

    /**
     * This method is allowed to get all components
     *
     * @return {@link ResponseEntity<CommonResponse>} - all components
     * @author @maleeshasa
     */
    @GetMapping(value = "/get-all")
    public ResponseEntity<CommonResponse> getAll() {
        log.info("ComponentController.getAll() => started.");
        return ResponseEntity.ok(componentService.getAll());
    }

    /**
     * This method is allowed to get all components with pagination
     *
     * @param page {@link int} - page number
     * @param size {@link int} - page size
     * @return {@link ResponseEntity<CommonResponse>} - all components
     * @author @maleeshasa
     */
    @GetMapping(value = "/get-all-page")
    public ResponseEntity<CommonResponse> getAllWithPage(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("ComponentController.getAllWithPage() => started.");
        return ResponseEntity.ok(componentService.getAllWithPage(PageRequest.of(page, size)));
    }

    /**
     * This method is allowed to get all components by scope and module
     *
     * @param scope    {@link String} - scope uuid
     * @param moduleId {@link int} - module id
     * @return {@link ResponseEntity<CommonResponse>} - all components by scope and module
     * @author @maleeshasa
     */
    @GetMapping(value = "/get-by-scope-and-module")
    public ResponseEntity<CommonResponse> getByScopeAndModule(@RequestParam(value = "scope") String scope,
                                                              @RequestParam(value = "module") int moduleId) {
        log.info("ComponentController.getByScopeAndModule() => started.");
        return ResponseEntity.ok(componentService.getByScopeAndModule(scope, moduleId));
    }

    @PostMapping(value = "/delete-by-id")
    public ResponseEntity<CommonResponse> deleteById(@RequestParam(value = "id") int id) {
        log.info("ComponentController.deleteById() => started.");
        return ResponseEntity.ok(componentService.deleteById(id));
    }
}
