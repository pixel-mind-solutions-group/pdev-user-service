package com.pdev.user_service.service.validation.authorizePartyProfile;

import com.pdev.user_service.dto.authorizePartyProfile.AuthorizePartyProfileRequestDTO;
import com.pdev.user_service.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ValidateAuthorizePartyProfile {

    public void validateAuthorizePartyProfile(AuthorizePartyProfileRequestDTO authorizePartyProfileRequest) {
        if (authorizePartyProfileRequest.getAuthorizeParty() == null) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "Authorize party is required.");

        } else if (authorizePartyProfileRequest.getAuthorizePartyRoles().isEmpty()) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "Authorize party roles are not selected.");
        }
    }
}
