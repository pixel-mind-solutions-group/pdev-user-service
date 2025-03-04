package com.pdev.user_service.mapper.authorizePartyRole;

import com.pdev.user_service.dto.authorizePartyRole.AuthorizePartyRoleRequestDTO;
import com.pdev.user_service.dto.authorizePartyRole.AuthorizePartyRoleResponseDTO;
import com.pdev.user_service.enums.CommonStatus;
import com.pdev.user_service.model.authorizePartyRole.AuthorizePartyRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Slf4j
@Component
public class AuthorizePartyRoleMapper {

    public AuthorizePartyRoleResponseDTO mapToDTO(AuthorizePartyRoleResponseDTO dto, AuthorizePartyRole authorizePartyRole) {
        log.info("AuthorizePartyRoleMapper.mapToDTO() => started.");
        dto.setId(authorizePartyRole.getId());
        dto.setActive(authorizePartyRole.getActive());
        dto.setRole(authorizePartyRole.getRole());
        log.info("AuthorizePartyRoleMapper.mapToDTO() => ended.");
        return dto;
    }

    public List<AuthorizePartyRoleResponseDTO> mapToList(List<AuthorizePartyRole> authorizePartyRoles) {
        log.info("AuthorizePartyRoleMapper.mapToList() => started.");
        List<AuthorizePartyRoleResponseDTO> dtoList = new ArrayList<>();
        if (!authorizePartyRoles.isEmpty()) {
            dtoList = authorizePartyRoles.stream()
                    .map(role -> mapToDTO(new AuthorizePartyRoleResponseDTO(), role))
                    .toList();
        }
        log.info("AuthorizePartyRoleMapper.mapToList() => ended.");
        return dtoList;
    }

    public AuthorizePartyRole mapToEntity(AuthorizePartyRole authorizePartyRole, AuthorizePartyRoleRequestDTO authorizePartyRoleRequest) {
        authorizePartyRole.setActive(authorizePartyRoleRequest.getStatus().equals(CommonStatus.ACTIVE.getValue()) ?
                Boolean.TRUE : Boolean.FALSE);
        authorizePartyRole.setRole(authorizePartyRoleRequest.getRole());
        return authorizePartyRole;
    }

    public List<AuthorizePartyRoleResponseDTO> mapToDTOList(List<AuthorizePartyRole> authorizePartyRoles) {
        List<AuthorizePartyRoleResponseDTO> dtoList = new ArrayList<>();
        if (!authorizePartyRoles.isEmpty()) {
            dtoList = authorizePartyRoles.stream()
                    .map(authorizePartyRole -> mapToDTO(new AuthorizePartyRoleResponseDTO(), authorizePartyRole))
                    .toList();
        }
        return dtoList;
    }
}
