package com.pdev.user_service.mapper.component;

import com.pdev.user_service.dto.component.ComponentRequestDTO;
import com.pdev.user_service.dto.component.ComponentResponseDTO;
import com.pdev.user_service.enums.CommonStatus;
import com.pdev.user_service.mapper.componentElement.ComponentElementMapper;
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
public class ComponentMapper {

    private final ComponentElementMapper componentElementMapper;

    public ComponentResponseDTO mapToDTO(ComponentResponseDTO dto,
                                         com.pdev.user_service.model.component.Component component) {
        log.info("ComponentMapper.mapToDTO() => started.");
        dto.setComponentId(component.getId());
        dto.setName(component.getElementName());
        dto.setKey(component.getName());
        dto.setActive(component.getActive());
        dto.setComponentElements(componentElementMapper.mapToList(component.getComponentElements()));
        log.info("ComponentMapper.mapToDTO() => ended.");
        return dto;
    }

    public List<ComponentResponseDTO> mapToList(List<com.pdev.user_service.model.component.Component> components) {
        log.info("ComponentMapper.mapToList() => started.");
        List<ComponentResponseDTO> dtoList = new ArrayList<>();
        if (!components.isEmpty()) {
            dtoList = components.stream()
                    .map(component -> mapToDTO(new ComponentResponseDTO(), component))
                    .toList();
        }
        log.info("ComponentMapper.mapToList() => ended.");
        return dtoList;
    }

    public com.pdev.user_service.model.component.Component mapToEntity(com.pdev.user_service.model.component.Component component,
                                                                       ComponentRequestDTO componentRequest) {
        log.info("ComponentMapper.mapToEntity() => started.");
        component.setName(componentRequest.getKey());
        component.setElementName(componentRequest.getName());
        component.setActive(componentRequest.getStatus().equals(CommonStatus.ACTIVE.getValue()) ? Boolean.TRUE : Boolean.FALSE);
        log.info("ComponentMapper.mapToEntity() => ended.");
        return component;
    }
}

