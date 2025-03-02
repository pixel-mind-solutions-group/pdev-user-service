package com.pdev.user_service.dto.component;

import com.pdev.user_service.dto.componentElement.ComponentElementResponseDTO;
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
public class ComponentResponseDTO {
    private Integer component;
    private String name;
    private String key;
    private String status;
    private String applicationScope;
    private String module;
    private List<ComponentElementResponseDTO> componentElements = new ArrayList<>();
}
