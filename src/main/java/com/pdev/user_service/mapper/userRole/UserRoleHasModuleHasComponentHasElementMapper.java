package com.pdev.user_service.mapper.userRole;

import com.pdev.user_service.dto.userRole.UserRoleHasModuleHasComponentHasElementResponseDTO;
import com.pdev.user_service.model.userRole.UserRoleHasModuleHasComponentHasElement;
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
public class UserRoleHasModuleHasComponentHasElementMapper {

    public UserRoleHasModuleHasComponentHasElementResponseDTO mapToDTO(UserRoleHasModuleHasComponentHasElementResponseDTO dto,
                                                                       UserRoleHasModuleHasComponentHasElement userRoleHasModuleHasComponentHasElement) {
        log.info("UserRoleHasModuleHasComponentHasElementMapper.mapToDTO() => started.");
        dto.setUserRoleHasModuleHasComponentHasElementId(userRoleHasModuleHasComponentHasElement.getId());
        dto.setElementName(userRoleHasModuleHasComponentHasElement.getComponentElement().getName());
        dto.setEditable(userRoleHasModuleHasComponentHasElement.getEditable());
        log.info("UserRoleHasModuleHasComponentHasElementMapper.mapToDTO() => ended.");
        return dto;
    }

    public List<UserRoleHasModuleHasComponentHasElementResponseDTO> mapToList(List<UserRoleHasModuleHasComponentHasElement> userRoleHasModuleHasComponentHasElements) {
        log.info("UserRoleHasModuleHasComponentHasElementMapper.mapToList() => started.");
        List<UserRoleHasModuleHasComponentHasElementResponseDTO> dtoList = new ArrayList<>();
        if (!userRoleHasModuleHasComponentHasElements.isEmpty()) {
            dtoList = userRoleHasModuleHasComponentHasElements.stream()
                    .map(userRoleHasModuleHasComponentHasElement -> mapToDTO(
                            new UserRoleHasModuleHasComponentHasElementResponseDTO(), userRoleHasModuleHasComponentHasElement))
                    .toList();
        }
        log.info("UserRoleHasModuleHasComponentHasElementMapper.mapToList() => ended.");
        return dtoList;
    }
}
