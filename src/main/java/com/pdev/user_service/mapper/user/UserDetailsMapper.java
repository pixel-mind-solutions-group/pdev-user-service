package com.pdev.user_service.mapper.user;

import com.pdev.user_service.dto.user.userDetails.UserDetailsResponseDTO;
import com.pdev.user_service.dto.user.userHasApplicationScopeHasUserRole.UserHasApplicationScopeHasUserRoleResponseDTO;
import com.pdev.user_service.model.user.User;
import com.pdev.user_service.model.user.UserHasApplicationScopeHasUserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class UserDetailsMapper {

    private final UserHasApplicationScopeHasUserRoleMapper userHasApplicationScopeHasUserRoleMapper;

    public UserDetailsResponseDTO mapToDTO(UserDetailsResponseDTO dto,
                                           User user,
                                           UserHasApplicationScopeHasUserRole applicationScopeHasUserRole) {
        log.info("UserDetailsMapper.mapToDTO() => started.");
        dto.setIdUser(user.getId());
        dto.setEmail(user.getEmail());
        dto.setIsEmailVerified(user.getIsEmailVerified());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUserName(user.getUserName());
        dto.setActive(user.getActive());
        dto.setFailCount(user.getFailCount());
        dto.setUserHasApplicationScopeHasUserRole(
                userHasApplicationScopeHasUserRoleMapper.mapToDTO(
                        new UserHasApplicationScopeHasUserRoleResponseDTO(), applicationScopeHasUserRole)
        );
        log.info("UserDetailsMapper.mapToDTO() => ended.");
        return dto;
    }
}
