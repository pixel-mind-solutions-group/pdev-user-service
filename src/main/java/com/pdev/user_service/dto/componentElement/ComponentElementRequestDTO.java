package com.pdev.user_service.dto.componentElement;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author @maleeshasa
 * @Date 2025/02/22
 */
@Getter
@Setter
public class ComponentElementRequestDTO {
    private Integer componentElementId;
    private String key;
    private String name;
    private String status;
    private Integer moduleId;
    private String applicationScope;
    private Integer componentId;
    private List<ComponentElementDTO> componentElements = new ArrayList<>();

    public ComponentElementRequestDTO(String key, String name, String status) {
        this.key = key;
        this.name = name;
        this.status = status;
    }
}
