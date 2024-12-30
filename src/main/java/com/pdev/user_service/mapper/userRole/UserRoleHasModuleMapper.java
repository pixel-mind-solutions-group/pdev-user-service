package com.pdev.user_service.mapper.userRole;

import com.pdev.user_service.dto.userRole.UserRoleHasModuleResponseDTO;
import com.pdev.user_service.model.userRole.UserRoleHasModule;
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
public class UserRoleHasModuleMapper {

    private final UserRoleHasModuleHasComponentMapper userRoleHasModuleHasComponentMapper;

    public UserRoleHasModuleResponseDTO mapToDTO(UserRoleHasModuleResponseDTO dto, UserRoleHasModule userRoleHasModule) {
        log.info("UserRoleHasModuleMapper.mapToDTO() => started.");
        dto.setIdUserRoleHasModule(userRoleHasModule.getId());
        dto.setModuleName(userRoleHasModule.getModule().getName());
        dto.setUserRoleHasModuleHasComponents(userRoleHasModuleHasComponentMapper.mapToList(userRoleHasModule.getUserRoleHasModuleHasComponents()));
        log.info("UserRoleHasModuleMapper.mapToDTO() => ended.");
        return dto;
    }

    public List<UserRoleHasModuleResponseDTO> mapToList(List<UserRoleHasModule> userRoleHasModules) {
        log.info("UserRoleHasModuleMapper.mapToList() => started.");
        List<UserRoleHasModuleResponseDTO> dtoList = new ArrayList<>();
        if (!userRoleHasModules.isEmpty()) {
            dtoList = userRoleHasModules.stream()
                    .map(userRoleHasModule -> mapToDTO(new UserRoleHasModuleResponseDTO(), userRoleHasModule))
                    .toList();
        }
        log.info("UserRoleHasModuleMapper.mapToList() => ended.");
        return dtoList;
    }
}
