package com.pdev.user_service.dto.applicationScope;

import lombok.Getter;
import lombok.Setter;

/**
 * @author @maleeshasa
 * @Date 2025/02/21
 */
@Getter
@Setter
public class ApplicationScopeRequestDTO {
    private Integer applicationScopeId;
    private String scope;
    private String status;
    private String uniqueId;
}
