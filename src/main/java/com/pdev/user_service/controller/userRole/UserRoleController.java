package com.pdev.user_service.controller.userRole;

import com.pdev.user_service.dto.userRole.UserRoleRequestDTO;
import com.pdev.user_service.service.userRole.UserRoleService;
import com.pdev.user_service.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/iam/user-role/v1")
@RequiredArgsConstructor
public class UserRoleController {

    private final UserRoleService userRoleService;

    @PostMapping(value = "/create-or-update")
    public ResponseEntity<CommonResponse> createOrUpdate(@RequestBody UserRoleRequestDTO userRoleRequest) {
        log.info("UserRoleController.createOrUpdate() => started.");
        return ResponseEntity.ok(userRoleService.createOrUpdate(userRoleRequest));
    }

    @GetMapping(value = "/get-all")
    public ResponseEntity<CommonResponse> getAll() {
        log.info("UserRoleController.getAll() => started.");
        return ResponseEntity.ok(userRoleService.getAll());
    }

    @GetMapping(value = "/get-all-page")
    public ResponseEntity<CommonResponse> getAllWithPage(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("UserRoleController.getAllWithPage() => started.");
        return ResponseEntity.ok(userRoleService.getAllWithPage(PageRequest.of(page, size)));
    }

    @GetMapping(value = "/get-by-id")
    public ResponseEntity<CommonResponse> getById(@RequestParam(value = "id") int id) {
        log.info("UserRoleController.getById() => started.");
        return ResponseEntity.ok(userRoleService.getById(id));
    }

    @DeleteMapping(value = "/delete-by-id")
    public ResponseEntity<CommonResponse> deleteById(@RequestParam(value = "id") int id) {
        log.info("UserRoleController.deleteById() => started.");
        return ResponseEntity.ok(userRoleService.deleteById(id));
    }
}
