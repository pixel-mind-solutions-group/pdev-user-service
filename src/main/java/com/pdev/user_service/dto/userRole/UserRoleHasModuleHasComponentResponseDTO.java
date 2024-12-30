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
public class UserRoleHasModuleHasComponentResponseDTO {
    private Integer userRoleHasModuleHasComponentId;
    private String componentName;
    private List<UserRoleHasModuleHasComponentHasElementResponseDTO> userRoleHasModuleHasComponentHasElements = new ArrayList<>();
}
