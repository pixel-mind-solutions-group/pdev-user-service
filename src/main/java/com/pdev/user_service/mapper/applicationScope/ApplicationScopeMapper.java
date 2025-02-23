package com.pdev.user_service.mapper.applicationScope;

import com.pdev.user_service.dto.applicationScope.ApplicationScopeRequestDTO;
import com.pdev.user_service.dto.applicationScope.ApplicationScopeResponseDTO;
import com.pdev.user_service.enums.CommonStatus;
import com.pdev.user_service.mapper.authorizePartyRole.AuthorizePartyRoleMapper;
import com.pdev.user_service.mapper.module.ModuleMapper;
import com.pdev.user_service.model.applicationScope.ApplicationScope;
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
@Component
@RequiredArgsConstructor
public class ApplicationScopeMapper {

    private final AuthorizePartyRoleMapper authorizePartyRoleMapper;
    private final ModuleMapper moduleMapper;

    public ApplicationScopeResponseDTO mapToDTO(ApplicationScopeResponseDTO dto, ApplicationScope applicationScope) {
        log.info("ApplicationScopeMapper.mapToDTO() => started.");
        dto.setApplicationScopeId(applicationScope.getId());
        dto.setScope(applicationScope.getScope());
        dto.setUniqueId(applicationScope.getUniqueId());
        dto.setStatus(applicationScope.getActive() ? CommonStatus.ACTIVE.getValue() : CommonStatus.INACTIVE.getValue());
        dto.setModules(moduleMapper.mapToList(applicationScope.getModules()));
        log.info("ApplicationScopeMapper.mapToDTO() => ended.");
        return dto;
    }

    public ApplicationScope mapToEntity(ApplicationScope scope, ApplicationScopeRequestDTO dto) {
        log.info("ApplicationScopeMapper.mapToEntity() => started.");
        scope.setActive(dto.getStatus().equals(CommonStatus.ACTIVE.getValue()) ? Boolean.TRUE : Boolean.FALSE);
        scope.setScope(dto.getScope());
        log.info("ApplicationScopeMapper.mapToEntity() => ended.");
        return scope;
    }

    public List<ApplicationScopeResponseDTO> mapToDTOList(List<ApplicationScope> applicationScopes) {
        log.info("ApplicationScopeMapper.mapToList() => started.");
        List<ApplicationScopeResponseDTO> dtoList = new ArrayList<>();
        if (!applicationScopes.isEmpty()) {
            dtoList = applicationScopes.stream()
                    .map(applicationScope -> mapToDTO(new ApplicationScopeResponseDTO(), applicationScope))
                    .toList();
        }
        log.info("ApplicationScopeMapper.mapToList() => ended.");
        return dtoList;
    }
}
