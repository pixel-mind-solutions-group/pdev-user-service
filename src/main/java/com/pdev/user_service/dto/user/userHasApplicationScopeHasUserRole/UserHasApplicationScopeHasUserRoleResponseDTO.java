package com.pdev.user_service.dto.user.userHasApplicationScopeHasUserRole;

import com.pdev.user_service.dto.applicationScope.ApplicationScopeResponseDTO;
import com.pdev.user_service.dto.userRole.UserRoleResponseDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Getter
@Setter
public class UserHasApplicationScopeHasUserRoleResponseDTO {
    private Integer userHasApplicationScopeHasUserRoleId;
    private ApplicationScopeResponseDTO applicationScope;
    private UserRoleResponseDTO userRole;
    private Boolean active;
}
