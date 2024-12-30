package com.pdev.user_service.dto.applicationScope;

import com.pdev.user_service.dto.module.ModuleResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Getter
@Setter
public class ApplicationScopeResponseDTO {
    private Integer applicationScopeId;
    private String scope;
    private Boolean active;
    private String uniqueId;
    private List<ModuleResponseDTO> modules = new ArrayList<>();
}
