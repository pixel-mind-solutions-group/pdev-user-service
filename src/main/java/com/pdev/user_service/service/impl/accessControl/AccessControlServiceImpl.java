package com.pdev.user_service.service.impl.accessControl;

import com.pdev.user_service.controller.response.PageResponse;
import com.pdev.user_service.dto.accessControl.AccessControlRequestDTO;
import com.pdev.user_service.dto.accessControl.EditAccessControlResponseDTO;
import com.pdev.user_service.dto.component.ComponentResponseDTO;
import com.pdev.user_service.dto.component.ComponentsByModuleResponseDTO;
import com.pdev.user_service.dto.componentElement.ComponentElementsByComponentResponseDTO;
import com.pdev.user_service.dto.module.ModuleResponseDTO;
import com.pdev.user_service.exception.RecordNotFoundException;
import com.pdev.user_service.mapper.accessControl.AccessControlMapper;
import com.pdev.user_service.mapper.component.ComponentMapper;
import com.pdev.user_service.mapper.componentElement.ComponentElementMapper;
import com.pdev.user_service.mapper.module.ModuleMapper;
import com.pdev.user_service.model.AuditData;
import com.pdev.user_service.model.component.Component;
import com.pdev.user_service.model.componentElement.ComponentElement;
import com.pdev.user_service.model.module.Module;
import com.pdev.user_service.model.userRole.UserRole;
import com.pdev.user_service.model.userRole.UserRoleHasModule;
import com.pdev.user_service.model.userRole.UserRoleHasModuleHasComponent;
import com.pdev.user_service.model.userRole.UserRoleHasModuleHasComponentHasElement;
import com.pdev.user_service.repository.component.ComponentRepository;
import com.pdev.user_service.repository.componentElement.ComponentElementRepository;
import com.pdev.user_service.repository.module.ModuleRepository;
import com.pdev.user_service.repository.userRole.UserRoleHasModuleHasComponentHasElementRepository;
import com.pdev.user_service.repository.userRole.UserRoleHasModuleHasComponentRepository;
import com.pdev.user_service.repository.userRole.UserRoleHasModuleRepository;
import com.pdev.user_service.repository.userRole.UserRoleRepository;
import com.pdev.user_service.service.accessControl.AccessControlService;
import com.pdev.user_service.util.CommonResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessControlServiceImpl implements AccessControlService {

    private final ComponentElementMapper componentElementMapper;
    private final ComponentMapper componentMapper;
    private final ModuleMapper moduleMapper;
    private final AccessControlMapper accessControlMapper;
    private final UserRoleRepository userRoleRepository;
    private final ModuleRepository moduleRepository;
    private final ComponentElementRepository componentElementRepository;
    private final ComponentRepository componentRepository;
    private final UserRoleHasModuleRepository userRoleHasModuleRepository;
    private final UserRoleHasModuleHasComponentRepository userRoleHasModuleHasComponentRepository;
    private final UserRoleHasModuleHasComponentHasElementRepository userRoleHasModuleHasComponentHasElementRepository;

    @Override
    @Transactional
    public CommonResponse createOrUpdate(AccessControlRequestDTO accessControlRequest) {
        UserRole role = userRoleRepository.findById(accessControlRequest.getUserRole())
                .orElseThrow(() -> new RecordNotFoundException("User role not found"));

        List<UserRoleHasModule> userRoleHasModules = mapUserRoleHasModuleEntities(role, accessControlRequest.getModules());
        List<UserRoleHasModuleHasComponent> roleHasModuleHasComponents = mapUserRoleHasModuleHasComponentEntities(userRoleHasModules, accessControlRequest.getComponents());
        List<UserRoleHasModuleHasComponentHasElement> userRoleHasModuleHasComponentHasElements = mapUserRoleHasModuleHasComponentHasElementEntities(roleHasModuleHasComponents, accessControlRequest.getComponentElements());

        try {
            List<UserRoleHasModule> existingModules = userRoleHasModuleRepository.findByUserRole(role);

            log.info("Deleting {} modules.", existingModules.size());
            role.getUserRoleHasModules().clear();
            userRoleHasModuleRepository.deleteAll(existingModules);

            // Creating new access settings
            userRoleHasModuleRepository.saveAll(userRoleHasModules);
            userRoleHasModuleHasComponentRepository.saveAll(roleHasModuleHasComponents);
            userRoleHasModuleHasComponentHasElementRepository.saveAll(userRoleHasModuleHasComponentHasElements);
            return new CommonResponse(HttpStatus.CREATED, "Access control created successfully", null);
        } catch (Exception e) {
            log.error("Error occurred while creating access control. Error: {}", e.getMessage());
            return new CommonResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while creating access control.", null);
        }
    }

    @Override
    public CommonResponse getAllWithPagination(PageRequest pageRequest) {
        log.info("AccessControlServiceImpl.getAllWithPagination() => started.");
        CommonResponse commonResponse = new CommonResponse();

        Page<UserRoleHasModule> userRoleHasModules = userRoleHasModuleRepository.findAll(pageRequest);
        Set<UserRole> userRoles = userRoleHasModules.getContent().stream().map(UserRoleHasModule::getUserRole).collect(Collectors.toSet());

        if (!userRoleHasModules.isEmpty()) {
            log.info("Access controls are exists.");
            // Constructing page response as pagination
            PageResponse pageResponse = PageResponse.builder()
                    .totalPages(userRoleHasModules.getTotalPages())
                    .totalElements(userRoleHasModules.getTotalElements())
                    .currentPage(userRoleHasModules.getNumber())
                    .dataList(accessControlMapper.mapToDTOList(userRoles)).build();

            commonResponse.setData(pageResponse);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setMessage("Access controls are exists.");
            return commonResponse;

        } else {
            log.info("Access controls are not exists.");
            commonResponse.setData(null);
            commonResponse.setStatus(HttpStatus.NO_CONTENT);
            commonResponse.setMessage("Access controls are not exists.");
            return commonResponse;
        }
    }

    private List<UserRoleHasModule> mapUserRoleHasModuleEntities(UserRole role, List<Integer> modules) {
        List<UserRoleHasModule> userRoleHasModules = new ArrayList<>();
        for (Integer m : modules) {
            Module module = moduleRepository.findById(m)
                    .orElseThrow(() -> new RecordNotFoundException("Module not found."));
            if (!module.getActive()) {
                throw new RecordNotFoundException("Module is in-active: " + module.getElementName() + "(" + module.getName() + ")");
            }

            UserRoleHasModule userRoleHasModule = new UserRoleHasModule();
            userRoleHasModule.setUserRole(role);
            userRoleHasModule.setModule(module);
            userRoleHasModule.setAuditData(new AuditData(LocalDateTime.now(), "system"));
            userRoleHasModules.add(userRoleHasModule);
        }
        return userRoleHasModules;
    }

    private List<UserRoleHasModuleHasComponent> mapUserRoleHasModuleHasComponentEntities(List<UserRoleHasModule> userRoleHasModules, List<Integer> components) {
        List<UserRoleHasModuleHasComponent> userRoleHasModuleHasComponents = new ArrayList<>();

        for (UserRoleHasModule userRoleHasModule : userRoleHasModules) {
            List<Component> componentList = componentRepository.findByModule(userRoleHasModule.getModule());
            for (Integer c : components) {
                Component component = componentRepository.findById(c)
                        .orElseThrow(() -> new RecordNotFoundException("Component not found."));
                if (!component.getActive()) {
                    throw new RecordNotFoundException("Component is in-active: " + component.getElementName() + "(" + component.getName() + ")");
                }
                if (componentList.contains(component)) {
                    UserRoleHasModuleHasComponent userRoleHasModuleHasComponent = new UserRoleHasModuleHasComponent();
                    userRoleHasModuleHasComponent.setUserRoleHasModule(userRoleHasModule);
                    userRoleHasModuleHasComponent.setComponent(component);
                    userRoleHasModuleHasComponent.setAuditData(new AuditData(LocalDateTime.now(), "system"));
                    userRoleHasModuleHasComponents.add(userRoleHasModuleHasComponent);
                }
            }
        }
        return userRoleHasModuleHasComponents;
    }

    private List<UserRoleHasModuleHasComponentHasElement> mapUserRoleHasModuleHasComponentHasElementEntities(List<UserRoleHasModuleHasComponent> roleHasModuleHasComponents,
                                                                                                             List<Integer> componentElements) {
        List<UserRoleHasModuleHasComponentHasElement> userRoleHasModuleHasComponentHasElements = new ArrayList<>();

        for (UserRoleHasModuleHasComponent userRoleHasModuleHasComponent : roleHasModuleHasComponents) {
            List<ComponentElement> componentElementList = componentElementRepository.findByComponent(userRoleHasModuleHasComponent.getComponent());
            for (Integer ce : componentElements) {
                ComponentElement componentElement = componentElementRepository.findById(ce)
                        .orElseThrow(() -> new RecordNotFoundException("Component element not found."));
                if (!componentElement.getActive()) {
                    throw new RecordNotFoundException("Component element is in-active: " + componentElement.getElementName() + "(" + componentElement.getName() + ")");
                }
                if (componentElementList.contains(componentElement)) {
                    UserRoleHasModuleHasComponentHasElement userRoleHasModuleHasComponentHasElement = new UserRoleHasModuleHasComponentHasElement();
                    userRoleHasModuleHasComponentHasElement.setUserRoleHasModuleHasComponent(userRoleHasModuleHasComponent);
                    userRoleHasModuleHasComponentHasElement.setComponentElement(componentElement);
                    userRoleHasModuleHasComponentHasElement.setAuditData(new AuditData(LocalDateTime.now(), "system"));
                    userRoleHasModuleHasComponentHasElements.add(userRoleHasModuleHasComponentHasElement);
                    userRoleHasModuleHasComponentHasElement.setEditable(Boolean.TRUE);
                }
            }
        }
        return userRoleHasModuleHasComponentHasElements;
    }

    @Override
    public CommonResponse getById(int id) {
        UserRole role = userRoleRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("User role not found."));
        List<UserRoleHasModule> userRoleHasModules = userRoleHasModuleRepository.findByUserRole(role);
        List<ModuleResponseDTO> moduleResponses = moduleMapper.mapToList(userRoleHasModules.stream().map(UserRoleHasModule::getModule).toList());

        List<ComponentsByModuleResponseDTO> componentsByModules = new ArrayList<>();
        for (UserRoleHasModule userRoleHasModule : userRoleHasModules) {
            ComponentsByModuleResponseDTO componentsByModule = new ComponentsByModuleResponseDTO();
            componentsByModule.setModule(moduleMapper.mapToDTO(new ModuleResponseDTO(), userRoleHasModule.getModule()));
            componentsByModule.setComponents(componentMapper.mapToList(userRoleHasModule.getUserRoleHasModuleHasComponents().stream().map(UserRoleHasModuleHasComponent::getComponent).toList()));
            componentsByModules.add(componentsByModule);
        }

        List<ComponentElementsByComponentResponseDTO> componentElementsByComponents = new ArrayList<>();
        for (UserRoleHasModule userRoleHasModule : userRoleHasModules) {
            for (UserRoleHasModuleHasComponent userRoleHasModuleHasComponent : userRoleHasModule.getUserRoleHasModuleHasComponents()) {
                ComponentElementsByComponentResponseDTO response = new ComponentElementsByComponentResponseDTO();
                response.setComponent(componentMapper.mapToDTO(new ComponentResponseDTO(), userRoleHasModuleHasComponent.getComponent()));
                response.setElements(componentElementMapper.mapToList(userRoleHasModuleHasComponent.getUserRoleHasModuleHasComponentHasElements().stream().map(UserRoleHasModuleHasComponentHasElement::getComponentElement).toList()));
                componentElementsByComponents.add(response);
            }
        }

        EditAccessControlResponseDTO dto = new EditAccessControlResponseDTO();
        dto.setModuleResponses(moduleResponses);
        dto.setComponentsByModules(componentsByModules);
        dto.setComponentElementsByComponents(componentElementsByComponents);
        return new CommonResponse(HttpStatus.OK, "Access controls are exists.", dto);
    }
}
