package com.pdev.user_service.mapper.userRole;

import com.pdev.user_service.dto.applicationScope.ApplicationScopeResponseDTO;
import com.pdev.user_service.dto.userRole.UserRoleRequestDTO;
import com.pdev.user_service.dto.userRole.UserRoleResponseDTO;
import com.pdev.user_service.enums.CommonStatus;
import com.pdev.user_service.mapper.applicationScope.ApplicationScopeMapper;
import com.pdev.user_service.model.userRole.UserRole;
import com.pdev.user_service.repository.applicationScope.ApplicationScopeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
    private final ApplicationScopeRepository applicationScopeRepository;

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

    public UserRole mapToEntity(UserRole userRole, UserRoleRequestDTO userRoleRequest) {
        log.info("UserRoleMapper.mapToEntity() => started.");
        userRole.setRole(userRoleRequest.getRole());
        userRole.setActive(userRoleRequest.getStatus().equalsIgnoreCase(CommonStatus.ACTIVE.getValue()) ? Boolean.TRUE : Boolean.FALSE);
        userRole.setApplicationScope(applicationScopeRepository.findByUniqueId(userRoleRequest.getApplicationScope()));
        log.info("UserRoleMapper.mapToEntity() => ended.");
        return userRole;
    }

    public List<UserRoleResponseDTO> mapToList(List<UserRole> userRoles) {
        List<UserRoleResponseDTO> dtoList = new ArrayList<>();
        userRoles.forEach(r -> {
            dtoList.add(mapToDTO(new UserRoleResponseDTO(), r));
        });
        return dtoList;
    }
}
