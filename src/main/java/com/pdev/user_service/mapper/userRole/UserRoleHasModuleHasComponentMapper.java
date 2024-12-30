package com.pdev.user_service.mapper.userRole;

import com.pdev.user_service.dto.userRole.UserRoleHasModuleHasComponentResponseDTO;
import com.pdev.user_service.model.userRole.UserRoleHasModuleHasComponent;
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
public class UserRoleHasModuleHasComponentMapper {

    private final UserRoleHasModuleHasComponentHasElementMapper userRoleHasModuleHasComponentHasElementMapper;

    public UserRoleHasModuleHasComponentResponseDTO mapToDTO(UserRoleHasModuleHasComponentResponseDTO dto,
                                                             UserRoleHasModuleHasComponent userRoleHasModuleHasComponent) {
        log.info("UserRoleHasModuleHasComponentMapper.mapToDTO() => started.");
        dto.setUserRoleHasModuleHasComponentId(userRoleHasModuleHasComponent.getId());
        dto.setComponentName(userRoleHasModuleHasComponent.getComponent().getName());
        dto.setUserRoleHasModuleHasComponentHasElements(userRoleHasModuleHasComponentHasElementMapper.mapToList(userRoleHasModuleHasComponent.getUserRoleHasModuleHasComponentHasElements()));
        log.info("UserRoleHasModuleHasComponentMapper.mapToDTO() => ended.");
        return dto;
    }

    public List<UserRoleHasModuleHasComponentResponseDTO> mapToList(List<UserRoleHasModuleHasComponent> userRoleHasModuleHasComponents) {
        log.info("UserRoleHasModuleHasComponentMapper.mapToList() => started.");
        List<UserRoleHasModuleHasComponentResponseDTO> dtoList = new ArrayList<>();
        if (!userRoleHasModuleHasComponents.isEmpty()) {
            dtoList = userRoleHasModuleHasComponents.stream()
                    .map(userRoleHasModuleHasComponent -> mapToDTO(new UserRoleHasModuleHasComponentResponseDTO(), userRoleHasModuleHasComponent))
                    .toList();
        }
        log.info("UserRoleHasModuleHasComponentMapper.mapToList() => ended.");
        return dtoList;
    }
}
