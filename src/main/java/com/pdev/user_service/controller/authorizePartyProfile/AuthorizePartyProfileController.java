package com.pdev.user_service.controller.authorizePartyProfile;

import com.pdev.user_service.dto.authorizePartyProfile.AuthorizePartyProfileRequestDTO;
import com.pdev.user_service.service.authorizePartyProfile.AuthorizePartyProfileService;
import com.pdev.user_service.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/iam/authorize-party-profile/v1")
public class AuthorizePartyProfileController {

    private final AuthorizePartyProfileService authorizePartyProfileService;

    /**
     * This method is allowed to create or update authorize party profile
     *
     * @param authorizePartyProfileRequest {@link AuthorizePartyProfileRequestDTO} - authorize party profile request
     * @return {@link ResponseEntity<CommonResponse>} - authorize party profile created or updated response
     * @author @maleeshasa
     */
    @PostMapping(value = "/create-or-update")
    public ResponseEntity<CommonResponse> createOrUpdate(@RequestBody AuthorizePartyProfileRequestDTO authorizePartyProfileRequest) {
        log.info("AuthorizePartyProfileController.createOrUpdate() => started.");
        return ResponseEntity.ok(authorizePartyProfileService.createOrUpdate(authorizePartyProfileRequest));
    }

    /**
     * This method is allowed to get all authorize party profiles
     *
     * @return {@link ResponseEntity<CommonResponse>} - all authorize party profiles
     * @author @maleeshasa
     */
    @GetMapping(value = "/get-all")
    public ResponseEntity<CommonResponse> getAll() {
        log.info("AuthorizePartyProfileController.getAll() => started.");
        return ResponseEntity.ok(authorizePartyProfileService.getAll());
    }

    /**
     * This method is allowed to get all authorize party profiles with pagination
     *
     * @param page {@link int} - page number
     * @param size {@link int} - page size
     * @return {@link ResponseEntity<CommonResponse>} - all authorize party profiles
     * @author @maleeshasa
     */
    @GetMapping(value = "/get-all-page")
    public ResponseEntity<CommonResponse> getAllWithPage(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("AuthorizePartyProfileController.getAllWithPage() => started.");
        return ResponseEntity.ok(authorizePartyProfileService.getAllWithPage(PageRequest.of(page, size)));
    }

    @GetMapping(value = "/get-by-auth-party")
    public ResponseEntity<CommonResponse> getAllByAuthParty(@RequestParam(value = "id") Integer id) {
        return ResponseEntity.ok(authorizePartyProfileService.getAllByAuthParty(id));
    }
}
