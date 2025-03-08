package com.pdev.user_service.dto.userRole;

import lombok.Getter;
import lombok.Setter;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Getter
@Setter
public class UserRoleRequestDTO {
    private Integer id;
    private String role;
    private String applicationScope;
    private String status;
}
