package com.pdev.user_service.dto.module;

import com.pdev.user_service.dto.component.ComponentResponseDTO;
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
public class ModuleResponseDTO {
    private Integer id;
    private String name;
    private Boolean active;
    private List<ComponentResponseDTO> components = new ArrayList<>();
}
