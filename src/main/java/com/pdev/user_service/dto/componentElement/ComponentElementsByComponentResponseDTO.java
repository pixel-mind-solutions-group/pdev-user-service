package com.pdev.user_service.dto.componentElement;

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
public class ComponentElementsByComponentResponseDTO {
    private ComponentResponseDTO component;
    private List<ComponentElementResponseDTO> elements = new ArrayList<>();
}
