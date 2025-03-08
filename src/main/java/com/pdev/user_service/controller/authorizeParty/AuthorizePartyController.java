package com.pdev.user_service.controller.authorizeParty;

import com.pdev.user_service.dto.authorizeParty.AuthorizePartyRequestDTO;
import com.pdev.user_service.service.authorizeParty.AuthorizePartyService;
import com.pdev.user_service.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class is allowed to manage authorize party
 *
 * @author maleeshasa
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/iam/authorize-party/v1")
@RequiredArgsConstructor
public class AuthorizePartyController {

    private final AuthorizePartyService authorizePartyService;

    /**
     * This method is allowed to create or update authorize party
     *
     * @param authorizePartyRequest {@link AuthorizePartyRequestDTO} - authorize party request
     * @return {@link ResponseEntity<CommonResponse>} - authorize party created or updated response
     * @author @maleeshasa
     */
    @PostMapping(value = "/create-or-update")
    public ResponseEntity<CommonResponse> createOrUpdate(@RequestBody AuthorizePartyRequestDTO authorizePartyRequest) {
        log.info("AuthorizePartyController.createOrUpdate() => started.");
        return ResponseEntity.ok(authorizePartyService.createOrUpdate(authorizePartyRequest));
    }

    /**
     * This method is allowed to get all authorize parties
     *
     * @return {@link ResponseEntity<CommonResponse>} - all authorize parties
     * @author @maleeshasa
     */
    @GetMapping(value = "/get-all")
    public ResponseEntity<CommonResponse> getAll() {
        log.info("AuthorizePartyController.getAll() => started.");
        return ResponseEntity.ok(authorizePartyService.getAll());
    }

    /**
     * This method is allowed to get all authorize parties with pagination
     *
     * @param page {@link int} - page number
     * @param size {@link int} - page size
     * @return {@link ResponseEntity<CommonResponse>} - all authorize parties
     * @author @maleeshasa
     */
    @GetMapping(value = "/get-all-page")
    public ResponseEntity<CommonResponse> getAllWithPage(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("AuthorizePartyController.getAllWithPage() => started.");
        return ResponseEntity.ok(authorizePartyService.getAllWithPage(PageRequest.of(page, size)));
    }

    @GetMapping(value = "/get-by-id")
    public ResponseEntity<CommonResponse> getById(@RequestParam(value = "id") int id) {
        log.info("AuthorizePartyController.getById() => started.");
        return ResponseEntity.ok(authorizePartyService.getById(id));
    }

    @PostMapping(value = "/delete-by-id")
    public ResponseEntity<CommonResponse> deleteById(@RequestParam(value = "id") int id) {
        log.info("AuthorizePartyController.deleteById() => started.");
        return ResponseEntity.ok(authorizePartyService.deleteById(id));
    }
}