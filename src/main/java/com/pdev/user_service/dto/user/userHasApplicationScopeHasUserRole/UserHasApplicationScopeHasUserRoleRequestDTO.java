package com.pdev.user_service.dto.user.userHasApplicationScopeHasUserRole;

import com.pdev.user_service.dto.user.hasElement.RoleHasModuleHasComponentHasElementRequestDTO;
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
public class UserHasApplicationScopeHasUserRoleRequestDTO {
    private Integer userHasApplicationScopeHasUserRoleId;
    private Integer applicationScopeId;
    private Integer userRoleId;
    private List<RoleHasModuleHasComponentHasElementRequestDTO> hasElements = new ArrayList<>();
}
