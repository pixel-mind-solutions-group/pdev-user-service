package com.pdev.user_service.mapper.user;

import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.dto.user.UserResponseDTO;
import com.pdev.user_service.enums.CommonStatus;
import com.pdev.user_service.mapper.authorizeParty.AuthorizePartyMapper;
import com.pdev.user_service.model.user.User;
import com.pdev.user_service.model.user.UserHasAuthorizeParty;
import com.pdev.user_service.repository.user.UserHasAuthorizePartyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class UserAccountMapper {

    private final AuthorizePartyMapper authorizePartyMapper;
    private final UserHasAuthorizePartyMapper userHasAuthorizePartyMapper;
    private final UserHasApplicationScopeHasUserRoleMapper userHasApplicationScopeHasUserRoleMapper;
    private final UserHasAuthorizePartyRepository userHasAuthorizePartyRepository;

    public UserResponseDTO mapToDTO(UserResponseDTO dto, User user) {
        log.info("UserAccountMapper.mapToDTO() => started.");
        dto.setIdUser(user.getId());
        dto.setEmail(user.getEmail());
        dto.setIsEmailVerified(user.getIsEmailVerified());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUserName(user.getUserName());
        dto.setActive(user.getActive());
        dto.setStatus(user.getActive() ? CommonStatus.ACTIVE.getValue() : CommonStatus.INACTIVE.getValue());
        dto.setFailCount(user.getFailCount());
        dto.setAccountNonLocked(user.getAccountNonLocked());
        dto.setUserHasAuthorizeParties(
                authorizePartyMapper.mapToList(
                        user.getUserHasAuthorizeParties().stream()
                                .filter(uhap -> uhap.getActive().equals(Boolean.TRUE))
                                .map(UserHasAuthorizeParty::getAuthorizeParty)
                                .toList()
                ));
        dto.setUserHasApplicationScopeHasUserRoles(
                userHasApplicationScopeHasUserRoleMapper.mapToDTOList(user.getUserHasApplicationScopeHasUserRoles()));
        // Custom values for kcs_v1
        dto.setPassword(user.getPassword());
        Map<String, String> appScopeWithRole = new HashMap<>();
        user.getUserHasApplicationScopeHasUserRoles().forEach(obj -> {
            if (obj.getActive().equals(Boolean.TRUE)) {
                appScopeWithRole.put(obj.getApplicationScope().getScope(), obj.getUserRole().getRole());
            }
        });
        dto.setAppScopeWithRole(appScopeWithRole);
        log.info("UserAccountMapper.mapToDTO() => ended.");
        return dto;
    }

    public void mapToADEntity(User user, UserRequestDTO userRequest) {
        log.info("UserAccountMapper.mapToEntity() => started.");
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setActive(userRequest.getStatus().equals(CommonStatus.ACTIVE.getValue()) ? Boolean.TRUE : Boolean.FALSE);
        user.setEmail(userRequest.getEmail());
        user.setAccountNonLocked(userRequest.getLocked());
        user.setIsEmailVerified(userRequest.getEmailVerified());
        user.setUserName(userRequest.getUserName());
        user.setFailCount((short) 0);
        user.setUserHasAuthorizeParties(userHasAuthorizePartyMapper.mapToADEntities(userRequest, user));
        user.setUserHasApplicationScopeHasUserRoles(
                userHasApplicationScopeHasUserRoleMapper.mapToEntitiesForAD(userRequest.getUserHasApplicationScopeHasUserRoles(), user));
        log.info("UserAccountMapper.mapToEntity() => ended.");
    }

    public void mapToNonADEntity(User user, UserRequestDTO userRequest) {
        log.info("UserAccountMapper.mapToNonADEntity() => started.");
        user.setUserName(userRequest.getUserName());
        user.setEmail(userRequest.getEmail());
        user.setAccountNonLocked(Boolean.TRUE);
        user.setIsEmailVerified(Boolean.FALSE);
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setActive(userRequest.getActive());
        user.setFailCount((short) 0);
        user.setUserHasAuthorizeParties(userHasAuthorizePartyMapper.mapToNonADEntities(userRequest, user));
        user.setUserHasApplicationScopeHasUserRoles(
                userHasApplicationScopeHasUserRoleMapper.mapToEntitiesForNonAD(userRequest.getUserHasApplicationScopeHasUserRoles(), user));
        log.info("UserAccountMapper.mapToNonADEntity() => ended.");
    }

    public List<UserResponseDTO> mapToLazyResponseList(List<User> content) {
        List<UserResponseDTO> dtoList = new ArrayList<>();
        if (!content.isEmpty()) {
            dtoList = content.stream()
                    .filter(uar -> uar.getActive().equals(Boolean.TRUE))
                    .map(user -> mapToDTO(new UserResponseDTO(), user))
                    .toList();
        }
        return dtoList;
    }
}
