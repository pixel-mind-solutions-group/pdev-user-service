package com.pdev.user_service.service.validation.authorizePartyRole;

import com.pdev.user_service.dto.authorizePartyRole.AuthorizePartyRoleRequestDTO;
import com.pdev.user_service.exception.BaseException;
import com.pdev.user_service.service.validation.CommonValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ValidateAuthorizePartyRole {

    public void validateAuthorizePartyRole(AuthorizePartyRoleRequestDTO authorizePartyRoleRequest) {
        if (CommonValidation.stringNullValidation(authorizePartyRoleRequest.getRole())) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "Authorize party role is required.");
        } else if (CommonValidation.stringNullValidation(authorizePartyRoleRequest.getStatus())) {
            throw new BaseException(HttpStatus.NOT_FOUND.value(), "Authorize party role status is required.");
        }
    }
}
