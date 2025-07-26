package com.pdev.user_service.dto.user;

import com.pdev.user_service.dto.user.userHasApplicationScopeHasUserRole.UserHasApplicationScopeHasUserRoleRequestDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Getter
@Setter
public class UserRequestDTO {
    private Integer userId;
    private String email;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String newPassword;
    private String uuid;
    private Boolean active;
    private String status;
    private Boolean emailVerified;
    private Boolean locked;
    private List<Integer> userHasAuthorizePartyIds = new ArrayList<>();
    private List<String> userHasAuthorizeParties = new ArrayList<>();
    private List<UserHasApplicationScopeHasUserRoleRequestDTO> userHasApplicationScopeHasUserRoles = new ArrayList<>();
}
