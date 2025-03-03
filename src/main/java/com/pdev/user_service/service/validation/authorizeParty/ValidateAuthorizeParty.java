package com.pdev.user_service.service.validation.authorizeParty;

import com.pdev.user_service.dto.authorizeParty.AuthorizePartyRequestDTO;
import com.pdev.user_service.exception.BaseException;
import com.pdev.user_service.service.validation.CommonValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ValidateAuthorizeParty {

    public void validateAuthorizeParty(AuthorizePartyRequestDTO authorizePartyRequest) {
        if (CommonValidation.stringNullValidation(authorizePartyRequest.getParty())) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "Authorize party is required.");
        } else if (CommonValidation.stringNullValidation(authorizePartyRequest.getStatus())) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "Authorize party status is required.");
        }
    }
}
