package com.pdev.user_service.controller.authorizePartyRole;

import com.pdev.user_service.dto.authorizePartyRole.AuthorizePartyRoleRequestDTO;
import com.pdev.user_service.service.authorizePartyRole.AuthorizePartyRoleService;
import com.pdev.user_service.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class is allowed to manage authorize party role
 *
 * @author maleeshasa
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/iam/authorize-party-role/v1")
@RequiredArgsConstructor
public class AuthorizePartyRoleController {

    private final AuthorizePartyRoleService authorizePartyRoleService;

    /**
     * This method is allowed to create or update authorize party role
     *
     * @param authorizePartyRoleRequest {@link AuthorizePartyRoleRequestDTO} - authorize party role request
     * @return {@link ResponseEntity<CommonResponse>} - authorize party role created or updated response
     * @author @maleeshasa
     */
    @PostMapping(value = "/create-or-update")
    public ResponseEntity<CommonResponse> createOrUpdate(@RequestBody AuthorizePartyRoleRequestDTO authorizePartyRoleRequest) {
        log.info("AuthorizePartyRoleController.createOrUpdate() => started.");
        return ResponseEntity.ok(authorizePartyRoleService.createOrUpdate(authorizePartyRoleRequest));
    }

    /**
     * This method is allowed to get all authorize party roles
     *
     * @return {@link ResponseEntity<CommonResponse>} - all authorize party roles
     * @author @maleeshasa
     */
    @GetMapping(value = "/get-all")
    public ResponseEntity<CommonResponse> getAll() {
        log.info("AuthorizePartyRoleController.getAll() => started.");
        return ResponseEntity.ok(authorizePartyRoleService.getAll());
    }

    /**
     * This method is allowed to get all authorize party roles with pagination
     *
     * @param page {@link int} - page number
     * @param size {@link int} - page size
     * @return {@link ResponseEntity<CommonResponse>} - all authorize party roles
     * @author @maleeshasa
     */
    @GetMapping(value = "/get-all-page")
    public ResponseEntity<CommonResponse> getAllWithPage(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("AuthorizePartyRoleController.getAllWithPage() => started.");
        return ResponseEntity.ok(authorizePartyRoleService.getAllWithPage(PageRequest.of(page, size)));
    }

    @GetMapping(value = "/get-by-id")
    public ResponseEntity<CommonResponse> getById(@RequestParam(value = "id") int id) {
        log.info("AuthorizePartyRoleController.getById() => started.");
        return ResponseEntity.ok(authorizePartyRoleService.getById(id));
    }

    @PostMapping(value = "/delete-by-id")
    public ResponseEntity<CommonResponse> deleteById(@RequestParam(value = "id") int id) {
        log.info("AuthorizePartyRoleController.deleteById() => started.");
        return ResponseEntity.ok(authorizePartyRoleService.deleteById(id));
    }
}
