package com.pdev.user_service.controller.applicationScope;

import com.pdev.user_service.dto.applicationScope.ApplicationScopeRequestDTO;
import com.pdev.user_service.service.applicationScope.ApplicationScopeService;
import com.pdev.user_service.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * This method is allowed to fetch all application scopes with pagination
     *
     * @param page {@link int} - page number
     * @param size {@link int} - page size
     * @return {@link ResponseEntity<CommonResponse>} - fetched application scopes response
     * @author @maleeshasa
     */
    @GetMapping(value = "/get-all-page")
    public ResponseEntity<CommonResponse> getAllWithPagination(@RequestParam(value = "page", defaultValue = "0") int page,
                                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("ApplicationScopeController.getAllWithPagination() => started.");
        return ResponseEntity.ok(applicationScopeService.getAllWithPagination(PageRequest.of(page, size)));
    }

    @GetMapping(value = "/get-by-id")
    public ResponseEntity<CommonResponse> getById(@RequestParam(value = "id") Integer id) {
        return ResponseEntity.ok(applicationScopeService.getById(id));
    }

    /**
     * This method is allowed to fetch all application scopes
     *
     * @return {@link ResponseEntity<CommonResponse>} - fetched application scopes response
     * @author @maleeshasa
     */
    @GetMapping(value = "/get-all")
    public ResponseEntity<CommonResponse> getAll() {
        log.info("ApplicationScopeController.getAll() => started.");
        return ResponseEntity.ok(applicationScopeService.getAll());
    }

    @DeleteMapping(value = "/delete-by-id")
    public ResponseEntity<CommonResponse> deleteById(@RequestParam(value = "id") int id) {
        log.info("ApplicationScopeController.deleteById() => started.");
        return ResponseEntity.ok(applicationScopeService.deleteById(id));
    }
}
