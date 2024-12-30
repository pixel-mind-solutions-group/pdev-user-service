package com.pdev.user_service.dto.userRole;

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
public class UserRoleHasModuleResponseDTO {
    private Integer idUserRoleHasModule;
    private String moduleName;
    private List<UserRoleHasModuleHasComponentResponseDTO> userRoleHasModuleHasComponents = new ArrayList<>();
}
