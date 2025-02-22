package com.pdev.user_service.service.validation.applicationScope;

import com.pdev.user_service.dto.applicationScope.ApplicationScopeRequestDTO;
import com.pdev.user_service.exception.BaseException;
import com.pdev.user_service.repository.applicationScope.ApplicationScopeRepository;
import com.pdev.user_service.service.validation.CommonValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author @maleeshasa
 * @Date 2025/02/21
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class ValidateApplicationScope {

    private final ApplicationScopeRepository applicationScopeRepository;

    /**
     * This method is allowed to validate application scope request
     *
     * @param applicationScopeRequest {@link ApplicationScopeRequestDTO} - application scope request
     * @author @maleeshasa
     */
    public void validateApplicationScope(ApplicationScopeRequestDTO applicationScopeRequest) {
        log.info("ValidateApplicationScope.validateApplicationScope() => started.");
        boolean valid = CommonValidation.validStatus(applicationScopeRequest.getStatus());

        if (valid == Boolean.FALSE) {
            log.warn("Status is invalid.");
            throw new BaseException(422, "Status is invalid.");

        } else if (CommonValidation.stringNullValidation(applicationScopeRequest.getScope())) {
            log.warn("Application scope name is not exists.");
            throw new BaseException(422, "Application scope name is not exists.");
        }
        log.info("ValidateApplicationScope.validateApplicationScope() => ended.");
    }

    public boolean uniqueUUID(String uuid) {
        log.info("ValidateApplicationScope.uniqueUUID() => started.");
        return applicationScopeRepository.findByUniqueId(uuid) != null ? Boolean.FALSE : Boolean.TRUE;
    }
}
