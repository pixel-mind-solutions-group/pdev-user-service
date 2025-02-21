package com.pdev.user_service.service.applicationScope;

import com.pdev.user_service.dto.applicationScope.ApplicationScopeRequestDTO;
import com.pdev.user_service.util.CommonResponse;

/**
 * @author @maleeshasa
 * @Date 2025/02/21
 */
public interface ApplicationScopeService {

    /**
     * This method is allowed to create or update application scope
     *
     * @param applicationScopeRequest {@link ApplicationScopeRequestDTO} - application scope request
     * @return {@link CommonResponse} - created or updated response
     * @author @maleeshasa
     */
    CommonResponse createOrUpdateApplicationScope(ApplicationScopeRequestDTO applicationScopeRequest);
}
