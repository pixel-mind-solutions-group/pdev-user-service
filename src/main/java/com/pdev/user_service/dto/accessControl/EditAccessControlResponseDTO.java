package com.pdev.user_service.dto.accessControl;

import com.pdev.user_service.dto.component.ComponentsByModuleResponseDTO;
import com.pdev.user_service.dto.componentElement.ComponentElementsByComponentResponseDTO;
import com.pdev.user_service.dto.module.ModuleResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EditAccessControlResponseDTO {
    private List<ModuleResponseDTO> moduleResponses = new ArrayList<>();
    private List<ComponentsByModuleResponseDTO> componentsByModules = new ArrayList<>();
    private List<ComponentElementsByComponentResponseDTO> componentElementsByComponents = new ArrayList<>();
}
