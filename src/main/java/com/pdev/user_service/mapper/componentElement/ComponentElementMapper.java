package com.pdev.user_service.mapper.componentElement;

import com.pdev.user_service.dto.componentElement.ComponentElementResponseDTO;
import com.pdev.user_service.model.componentElement.ComponentElement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ComponentElementMapper {

    public ComponentElementResponseDTO mapToDTO(ComponentElementResponseDTO dto, ComponentElement componentElement) {
        log.info("ComponentElementMapper.mapToDTO() => started.");
        dto.setComponentElementId(componentElement.getId());
        dto.setName(componentElement.getName());
        dto.setActive(componentElement.getActive());
        log.info("ComponentElementMapper.mapToDTO() => ended.");
        return dto;
    }

    public List<ComponentElementResponseDTO> mapToList(List<ComponentElement> componentElements) {
        log.info("ComponentElementMapper.mapToList() => started.");
        List<ComponentElementResponseDTO> dtoList = new ArrayList<>();
        if (!componentElements.isEmpty()) {
            dtoList = componentElements.stream()
                    .map(componentElement -> mapToDTO(new ComponentElementResponseDTO(), componentElement))
                    .toList();
        }
        log.info("ComponentElementMapper.mapToList() => ended.");
        return dtoList;
    }
}

