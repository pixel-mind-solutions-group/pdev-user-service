package com.pdev.user_service.dto.user;

import com.pdev.user_service.dto.authorizeParty.AuthorizePartyResponseDTO;
import com.pdev.user_service.dto.user.userHasApplicationScopeHasUserRole.UserHasApplicationScopeHasUserRoleResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Getter
@Setter
public class UserResponseDTO {
    private Integer idUser;
    private String email;
    private Boolean isEmailVerified;
    private String firstName;
    private String lastName;
    private String userName;
    private Boolean active;
    private String status;
    private Short failCount;
    private Boolean accountNonLocked;
    private List<AuthorizePartyResponseDTO> userHasAuthorizeParties = new ArrayList<>();
    private List<UserHasApplicationScopeHasUserRoleResponseDTO> userHasApplicationScopeHasUserRoles = new ArrayList<>();

    // custom value for kcs_v1
    private Map<String, String> appScopeWithRole = new HashMap<>();
    private String password;
}
