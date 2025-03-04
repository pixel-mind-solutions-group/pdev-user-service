package com.pdev.user_service.dto.authorizePartyRole;

import lombok.Getter;
import lombok.Setter;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Getter
@Setter
public class AuthorizePartyRoleRequestDTO {
    private Integer id;
    private String role;
    private String status;
}
