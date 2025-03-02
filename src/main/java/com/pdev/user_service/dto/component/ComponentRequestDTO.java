package com.pdev.user_service.dto.component;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author @maleeshasa
 * @Date 2024/02/22
 */
@Getter
@Setter
public class ComponentRequestDTO {
    private Integer component;
    private String applicationScope;
    private Integer module;
    private String key;
    private String name;
    private String status;
    private List<ComponentDTO> components = new ArrayList<>();

    public ComponentRequestDTO(String key, String name, String status) {
        this.key = key;
        this.name = name;
        this.status = status;
    }
}
