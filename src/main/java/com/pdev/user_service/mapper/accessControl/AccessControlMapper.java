package com.pdev.user_service.mapper.accessControl;

import com.pdev.user_service.dto.accessControl.AccessControlResponseDTO;
import com.pdev.user_service.model.userRole.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccessControlMapper {

    public AccessControlResponseDTO mapToDTO(AccessControlResponseDTO dto, UserRole userRole) {
        log.info("AccessControlMapper.mapToDTO() => started.");
        dto.setUserRoleId(userRole.getId());
        dto.setUserRole(userRole.getRole());
        dto.setApplicationScope(userRole.getApplicationScope().getScope());
        log.info("AccessControlMapper.mapToDTO() => ended.");
        return dto;
    }

    public List<AccessControlResponseDTO> mapToDTOList(Set<UserRole> userRoles) {
        List<AccessControlResponseDTO> dtoList = new ArrayList<>();
        for (UserRole u : userRoles) {
            dtoList.add(mapToDTO(new AccessControlResponseDTO(), u));
        }
        return dtoList;
    }
}
