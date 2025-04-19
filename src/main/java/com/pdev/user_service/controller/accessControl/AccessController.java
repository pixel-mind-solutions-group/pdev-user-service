package com.pdev.user_service.controller.accessControl;

import com.pdev.user_service.dto.accessControl.AccessControlRequestDTO;
import com.pdev.user_service.service.accessControl.AccessControlService;
import com.pdev.user_service.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/get-all-page")
    public ResponseEntity<CommonResponse> getAllWithPagination(@RequestParam(value = "page", defaultValue = "0") int page,
                                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("AccessController.getAllWithPagination() => started.");
        return ResponseEntity.ok(accessControlService.getAllWithPagination(PageRequest.of(page, size)));
    }

    @GetMapping(value = "/get-by-id")
    public ResponseEntity<CommonResponse> getById(@RequestParam(value = "id") int id) {
        return ResponseEntity.ok(accessControlService.getById(id));
    }
}
