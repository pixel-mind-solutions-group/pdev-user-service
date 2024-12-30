package com.pdev.user_service.mapper.userRole;

import com.pdev.user_service.dto.applicationScope.ApplicationScopeResponseDTO;
import com.pdev.user_service.dto.userRole.UserRoleResponseDTO;
import com.pdev.user_service.mapper.applicationScope.ApplicationScopeMapper;
import com.pdev.user_service.model.userRole.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class UserRoleMapper {

    private final ApplicationScopeMapper applicationScopeMapper;
    private final UserRoleHasModuleMapper userRoleHasModuleMapper;

    public UserRoleResponseDTO mapToDTO(UserRoleResponseDTO dto, UserRole userRole) {
        log.info("UserRoleMapper.mapToDTO() => started.");
        dto.setUserRoleId(userRole.getId());
        dto.setRole(userRole.getRole());
        dto.setActive(userRole.getActive());
        dto.setApplicationScope(applicationScopeMapper.mapToDTO(new ApplicationScopeResponseDTO(), userRole.getApplicationScope()));
        dto.setUserRoleHasModules(userRoleHasModuleMapper.mapToList(userRole.getUserRoleHasModules()));
        log.info("UserRoleMapper.mapToDTO() => ended.");
        return dto;
    }
}
