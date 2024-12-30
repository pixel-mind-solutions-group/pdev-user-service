package com.pdev.user_service.mapper.module;

import com.pdev.user_service.dto.module.ModuleResponseDTO;
import com.pdev.user_service.mapper.component.ComponentMapper;
import com.pdev.user_service.model.module.Module;
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
public class ModuleMapper {

    private final ComponentMapper componentMapper;

    public ModuleResponseDTO mapToDTO(ModuleResponseDTO dto, Module module) {
        log.info("ModuleMapper.mapToDTO() => ended.");
        dto.setId(module.getId());
        dto.setName(module.getName());
        dto.setActive(module.getActive());
        dto.setComponents(componentMapper.mapToList(module.getComponents()));
        log.info("ModuleMapper.mapToDTO() => ended.");
        return dto;
    }

    public List<ModuleResponseDTO> mapToList(List<Module> modules) {
        log.info("ModuleMapper.mapToList() => ended.");
        List<ModuleResponseDTO> dtoList = new ArrayList<>();
        if (!modules.isEmpty()) {
            dtoList = modules.stream()
                    .map(module -> mapToDTO(new ModuleResponseDTO(), module))
                    .toList();
        }
        log.info("ModuleMapper.mapToList() => ended.");
        return dtoList;
    }
}

