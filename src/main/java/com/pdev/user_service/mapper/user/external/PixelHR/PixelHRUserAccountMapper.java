package com.pdev.user_service.mapper.user.external.PixelHR;

import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.dto.user.UserResponseDTO;
import com.pdev.user_service.dto.user.userHasApplicationScopeHasUserRole.UserHasApplicationScopeHasUserRoleResponseDTO;
import com.pdev.user_service.dto.userRole.UserRoleResponseDTO;
import com.pdev.user_service.enums.CommonStatus;
import com.pdev.user_service.mapper.authorizeParty.AuthorizePartyMapper;
import com.pdev.user_service.mapper.user.UserHasApplicationScopeHasUserRoleMapper;
import com.pdev.user_service.mapper.user.UserHasAuthorizePartyMapper;
import com.pdev.user_service.mapper.userRole.UserRoleMapper;
import com.pdev.user_service.model.user.UserHasApplicationScopeHasUserRole;
import com.pdev.user_service.model.user.UserHasAuthorizeParty;
import com.pdev.user_service.model.user.external.pixelHR.PixelHRUser;
import com.pdev.user_service.model.user.internal.User;
import com.pdev.user_service.service.validation.CommonValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class PixelHRUserAccountMapper {

    private final UserRoleMapper userRoleMapper;
    private final AuthorizePartyMapper authorizePartyMapper;
    private final UserHasAuthorizePartyMapper userHasAuthorizePartyMapper;
    private final UserHasApplicationScopeHasUserRoleMapper userHasApplicationScopeHasUserRoleMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserResponseDTO mapToDTO(UserResponseDTO dto, PixelHRUser user) {
        log.info("UserAccountMapper.mapToDTO() => started.");
        dto.setIdUser(user.getId());
        dto.setEmail(user.getEmail());
        dto.setIsEmailVerified(user.getIsEmailVerified());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUserName(user.getUserName());
        dto.setActive(user.getActive());
        dto.setFailCount(user.getFailCount());
        dto.setAccountNonLocked(user.getAccountNonLocked());
        log.info("UserAccountMapper.mapToDTO() => ended.");
        return dto;
    }

    public void mapToEntity(PixelHRUser user, UserRequestDTO userRequest) {
        log.info("UserAccountMapper.mapToEntity() => started.");
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setAccountNonLocked(userRequest.getLocked() == null ? Boolean.FALSE : Boolean.TRUE);
        user.setIsEmailVerified(userRequest.getEmailVerified());
        user.setUserName(userRequest.getUserName());
        user.setFailCount((short) 0);
        user.setIsEmailVerified(Boolean.TRUE);
        user.setActive(Boolean.TRUE);
        log.info("UserAccountMapper.mapToEntity() => ended.");
    }

    public UserResponseDTO mapToUpdateUserDTO(User byId) {
        UserResponseDTO userResponse = new UserResponseDTO();
        userResponse.setIdUser(byId.getId());
        userResponse.setIsLocked(byId.getAccountNonLocked() ? Boolean.FALSE : Boolean.TRUE);
        userResponse.setActive(byId.getActive());
        userResponse.setFirstName(byId.getFirstName());
        userResponse.setLastName(byId.getLastName());
        userResponse.setUserName(byId.getUserName());
        userResponse.setEmail(byId.getEmail());
        userResponse.setStatus(byId.getActive() ? CommonStatus.ACTIVE.getValue() : CommonStatus.INACTIVE.getValue());
        userResponse.setIsEmailVerified(byId.getIsEmailVerified());
        userResponse.setAccountNonLocked(byId.getAccountNonLocked());
        userResponse.setUserAuthParties(
                byId.getUserHasAuthorizeParties().stream()
                        .filter(UserHasAuthorizeParty::getActive)
                        .map(u -> u.getAuthorizeParty().getId()).toList()
        );
        userResponse.setUserHasApplicationScopeHasUserRoles(
                byId.getUserHasApplicationScopeHasUserRoles().stream()
                        .filter(UserHasApplicationScopeHasUserRole::getActive)
                        .map(uhr -> {
                            UserHasApplicationScopeHasUserRoleResponseDTO dto = new UserHasApplicationScopeHasUserRoleResponseDTO();
                            dto.setActive(uhr.getActive());
                            dto.setUserRole(userRoleMapper.mapToDTO(new UserRoleResponseDTO(), uhr.getUserRole()));
                            return dto;
                        }).toList());
        return userResponse;
    }
}
