package com.pdev.user_service.dto.accessControl;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AccessControlResponseDTO {
    private Integer userRoleId;
    private String applicationScope;
    private String userRole;
}
