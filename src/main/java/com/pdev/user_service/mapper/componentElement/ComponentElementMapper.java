package com.pdev.user_service.mapper.componentElement;

import com.pdev.user_service.dto.componentElement.ComponentElementRequestDTO;
import com.pdev.user_service.dto.componentElement.ComponentElementResponseDTO;
import com.pdev.user_service.enums.CommonStatus;
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
        dto.setName(componentElement.getElementName());
        dto.setKey(componentElement.getName());
        dto.setStatus(componentElement.getActive() ? CommonStatus.ACTIVE.getValue() : CommonStatus.INACTIVE.getValue());
        dto.setScope(componentElement.getApplicationScope().getUniqueId());
        dto.setModuleName(componentElement.getModule().getElementName());
        dto.setComponentName(componentElement.getComponent().getElementName());
        dto.setModule(componentElement.getModule().getId());
        dto.setComponent(componentElement.getComponent().getId());
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

    public ComponentElement mapToEntity(ComponentElement componentElement, ComponentElementRequestDTO dto) {
        log.info("ComponentElementMapper.mapToEntity() => started.");
        componentElement.setName(dto.getKey());
        componentElement.setElementName(dto.getName());
        componentElement.setActive(dto.getStatus().equals(CommonStatus.ACTIVE.getValue()) ? Boolean.TRUE : Boolean.FALSE);
        log.info("ComponentElementMapper.mapToEntity() => ended.");
        return componentElement;
    }
}

