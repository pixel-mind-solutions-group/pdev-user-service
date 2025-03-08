package com.pdev.user_service.controller.module;

import com.pdev.user_service.dto.module.ModuleRequestDTO;
import com.pdev.user_service.service.module.ModuleService;
import com.pdev.user_service.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
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

    /**
     * This method is allowed to get all modules with pagination
     *
     * @param page {@link int} - page number
     * @param size {@link int} - page size
     * @return {@link ResponseEntity<CommonResponse>} - all modules
     * @author @maleeshasa
     */
    @GetMapping(value = "/get-all-page")
    public ResponseEntity<CommonResponse> getAllWithPagination(@RequestParam(value = "page", defaultValue = "0") int page,
                                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("ModuleController.getAllWithPagination() => started.");
        return ResponseEntity.ok(moduleService.getAllWithPagination(PageRequest.of(page, size)));
    }

    @GetMapping(value = "/get-by-id")
    public ResponseEntity<CommonResponse> getById(@RequestParam(value = "id") Integer id) {
        return ResponseEntity.ok(moduleService.getById(id));
    }

    /**
     * This method is allowed to get modules by application scope
     *
     * @param uuid {@link String} -uuid
     * @return {@link ResponseEntity<CommonResponse>} - all modules
     * @author @maleeshasa
     */
    @GetMapping(value = "/get-by-uuid")
    public ResponseEntity<CommonResponse> getModulesByAppScope(@RequestParam(value = "uuid") String uuid) {
        log.info("ModuleController.getModulesByAppScope() => started.");
        return ResponseEntity.ok(moduleService.getModulesByAppScope(uuid));
    }

    @DeleteMapping(value = "/delete-by-id")
    public ResponseEntity<CommonResponse> deleteById(@RequestParam(value = "id") int id) {
        log.info("ModuleController.deleteById() => started.");
        return ResponseEntity.ok(moduleService.deleteById(id));
    }
}
