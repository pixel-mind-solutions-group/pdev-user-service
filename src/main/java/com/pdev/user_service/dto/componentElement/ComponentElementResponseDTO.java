package com.pdev.user_service.dto.componentElement;

import lombok.Getter;
import lombok.Setter;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Getter
@Setter
public class ComponentElementResponseDTO {
    private Integer componentElementId;
    private String name;
    private String key;
    private String status;
    private String scope;
    private String module;
    private String component;
}
