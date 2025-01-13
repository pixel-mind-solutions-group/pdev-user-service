package com.pdev.user_service.mapper.user;

import com.pdev.user_service.dto.applicationScope.ApplicationScopeResponseDTO;
import com.pdev.user_service.dto.user.userHasApplicationScopeHasUserRole.UserHasApplicationScopeHasUserRoleRequestDTO;
import com.pdev.user_service.dto.user.userHasApplicationScopeHasUserRole.UserHasApplicationScopeHasUserRoleResponseDTO;
import com.pdev.user_service.dto.userRole.UserRoleResponseDTO;
import com.pdev.user_service.exception.RecordNotFoundException;
import com.pdev.user_service.mapper.applicationScope.ApplicationScopeMapper;
import com.pdev.user_service.mapper.userRole.UserRoleMapper;
import com.pdev.user_service.model.AuditData;
import com.pdev.user_service.model.user.User;
import com.pdev.user_service.model.user.UserHasApplicationScopeHasUserRole;
import com.pdev.user_service.repository.applicationScope.ApplicationScopeRepository;
import com.pdev.user_service.repository.userRole.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserHasApplicationScopeHasUserRoleMapper {

    private final UserRoleRepository userRoleRepository;
    private final ApplicationScopeRepository applicationScopeRepository;

    private final ApplicationScopeMapper applicationScopeMapper;
    private final UserRoleMapper userRoleMapper;

    public UserHasApplicationScopeHasUserRoleResponseDTO mapToDTO(UserHasApplicationScopeHasUserRoleResponseDTO dto,
                                                                  UserHasApplicationScopeHasUserRole applicationScopeHasUserRole) {
        log.info("UserHasApplicationScopeHasUserRoleMapper.mapToDTO() => started.");
        dto.setUserHasApplicationScopeHasUserRoleId(applicationScopeHasUserRole.getId());
        dto.setUserRole(userRoleMapper.mapToDTO(new UserRoleResponseDTO(), applicationScopeHasUserRole.getUserRole()));
        dto.setApplicationScope(applicationScopeMapper.mapToDTO(new ApplicationScopeResponseDTO(), applicationScopeHasUserRole.getApplicationScope()));
        dto.setActive(applicationScopeHasUserRole.getActive());
        log.info("UserHasApplicationScopeHasUserRoleMapper.mapToDTO() => ended.");
        return dto;
    }

    public List<UserHasApplicationScopeHasUserRoleResponseDTO> mapToDTOList(List<UserHasApplicationScopeHasUserRole> userHasApplicationScopeHasUserRoles) {
        log.info("UserHasApplicationScopeHasUserRoleMapper.mapToList() => started.");
        List<UserHasApplicationScopeHasUserRoleResponseDTO> dtoList = new ArrayList<>();
        if (!userHasApplicationScopeHasUserRoles.isEmpty()) {
            dtoList = userHasApplicationScopeHasUserRoles.stream()
                    .filter(uar -> uar.getActive().equals(Boolean.TRUE))
                    .map(applicationScopeHasUserRole -> mapToDTO(new UserHasApplicationScopeHasUserRoleResponseDTO(), applicationScopeHasUserRole))
                    .toList();
        }
        log.info("UserHasApplicationScopeHasUserRoleMapper.mapToList() => ended.");
        return dtoList;
    }


    public List<UserHasApplicationScopeHasUserRole> mapToEntitiesForAD(List<UserHasApplicationScopeHasUserRoleRequestDTO> userHasApplicationScopeHasUserRoles,
                                                                       User user) {
        log.info("UserHasApplicationScopeHasUserRoleMapper.mapToEntities() => started.");
        List<UserHasApplicationScopeHasUserRole> entities = new ArrayList<>();

        userHasApplicationScopeHasUserRoles.forEach(dto -> {
            UserHasApplicationScopeHasUserRole entity = new UserHasApplicationScopeHasUserRole();
            entity.setUserRole(userRoleRepository.findById(dto.getUserRoleId())
                    .orElseThrow(() -> new RecordNotFoundException("User role is not exists.")));
            entity.setUser(user);
            entity.setApplicationScope(applicationScopeRepository.findById(dto.getApplicationScopeId())
                    .orElseThrow(() -> new RecordNotFoundException("Application scope is not exists.")));
            entity.setAuditData(new AuditData(LocalDateTime.now(), "admin"));
            entity.setActive(Boolean.TRUE);
            entities.add(entity);
        });
        return entities;
    }

    public List<UserHasApplicationScopeHasUserRole> mapToEntitiesForNonAD(List<UserHasApplicationScopeHasUserRoleRequestDTO> userHasApplicationScopeHasUserRoles,
                                                                          User user) {
        log.info("UserHasApplicationScopeHasUserRoleMapper.mapToEntitiesForNonAD() => started.");
        List<UserHasApplicationScopeHasUserRole> entities = new ArrayList<>();

        userHasApplicationScopeHasUserRoles.forEach(dto -> {
            UserHasApplicationScopeHasUserRole entity = new UserHasApplicationScopeHasUserRole();
            entity.setUserRole(userRoleRepository.findByRole(dto.getUserRole())
                    .orElseThrow(() -> new RecordNotFoundException("User role is not exists.")));
            entity.setUser(user);
            entity.setApplicationScope(applicationScopeRepository.findByScope(dto.getApplicationScope())
                    .orElseThrow(() -> new RecordNotFoundException("Application scope is not exists.")));
            entity.setAuditData(new AuditData(LocalDateTime.now(), "admin"));
            entity.setActive(Boolean.TRUE);
            entities.add(entity);
        });
        return entities;
    }
}
