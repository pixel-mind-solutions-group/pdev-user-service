package com.pdev.user_service.dto.userRole;

import com.pdev.user_service.dto.applicationScope.ApplicationScopeResponseDTO;
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
public class UserRoleResponseDTO {
    private Integer userRoleId;
    private String role;
    private Boolean active;
    private ApplicationScopeResponseDTO applicationScope;
    private List<UserRoleHasModuleResponseDTO> userRoleHasModules = new ArrayList<>();
}
