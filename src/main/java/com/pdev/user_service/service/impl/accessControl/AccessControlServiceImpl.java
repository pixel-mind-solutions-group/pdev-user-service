package com.pdev.user_service.service.impl.accessControl;

import com.pdev.user_service.dto.accessControl.AccessControlRequestDTO;
import com.pdev.user_service.exception.RecordNotFoundException;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessControlServiceImpl implements AccessControlService {

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
            // Deleting previous settings
//// Step 1: Delete Elements (deepest level)
//            userRoleHasModuleHasComponentHasElementRepository
//                    .deleteAllByUserRoleHasModuleHasComponentUserRoleHasModuleUserRole(role);
//
//// Step 2: Delete Components
//            userRoleHasModuleHasComponentRepository
//                    .deleteAllByUserRoleHasModuleUserRole(role);

// Step 3: Delete Modules (top level)
            List<UserRoleHasModule> existingModules = userRoleHasModuleRepository.findByUserRole(role);
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

    private List<UserRoleHasModule> mapUserRoleHasModuleEntities(UserRole role, List<Integer> modules) {
        List<UserRoleHasModule> userRoleHasModules = new ArrayList<>();
        for (Integer m : modules) {
            Module module = moduleRepository.findByIdAndActive(m, Boolean.TRUE);
            if (module == null) {
                throw new RecordNotFoundException("Active module not found");
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
                Component component = componentRepository.findByIdAndActive(c, Boolean.TRUE);
                if (component == null) {
                    throw new RecordNotFoundException("Active component not found");
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
                ComponentElement componentElement = componentElementRepository.findByIdAndActive(ce, Boolean.TRUE);
                if (componentElement == null) {
                    throw new RecordNotFoundException("Active component element not found");
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
}
