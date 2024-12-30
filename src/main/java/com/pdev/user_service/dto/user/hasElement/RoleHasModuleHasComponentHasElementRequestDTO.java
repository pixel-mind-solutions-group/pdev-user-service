package com.pdev.user_service.dto.user.hasElement;

import lombok.Getter;
import lombok.Setter;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Getter
@Setter
public class RoleHasModuleHasComponentHasElementRequestDTO {
    private Integer moduleId;
    private Integer componentId;
    private Integer elementId;
}
