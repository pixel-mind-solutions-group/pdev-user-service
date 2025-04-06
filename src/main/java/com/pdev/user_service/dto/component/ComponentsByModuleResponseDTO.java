package com.pdev.user_service.dto.component;

import com.pdev.user_service.dto.module.ModuleResponseDTO;
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
public class ComponentsByModuleResponseDTO {
    private ModuleResponseDTO module;
    private List<ComponentResponseDTO> components = new ArrayList<>();
}
