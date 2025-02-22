package com.pdev.user_service.service.module;

import com.pdev.user_service.dto.module.ModuleRequestDTO;
import com.pdev.user_service.util.CommonResponse;
import org.springframework.http.ResponseEntity;

/**
 * @author @maleeshasa
 * @Date 2025/02/22
 */
public interface ModuleService {

    /**
     * This method is allowed to create or update module
     *
     * @param moduleRequest {@link ModuleRequestDTO} - module request
     * @return {@link CommonResponse} - module created or updated response
     * @author @maleeshasa
     */
    CommonResponse createOrUpdate(ModuleRequestDTO moduleRequest);

    /**
     * This method is allowed to get all modules
     *
     * @return {@link ResponseEntity <CommonResponse>} - all modules
     * @author @maleeshasa
     */
    CommonResponse getAll();
}
