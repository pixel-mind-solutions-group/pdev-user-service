package com.pdev.user_service.dto.user.userDetails;

import com.pdev.user_service.dto.user.userHasApplicationScopeHasUserRole.UserHasApplicationScopeHasUserRoleResponseDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Getter
@Setter
public class UserDetailsResponseDTO {
    private Integer idUser;
    private String email;
    private Boolean isEmailVerified;
    private String firstName;
    private String lastName;
    private String userName;
    private Boolean active;
    private Short failCount;
    private String mfaStatus;
    private UserHasApplicationScopeHasUserRoleResponseDTO userHasApplicationScopeHasUserRole;
}
