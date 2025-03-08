package com.pdev.user_service.service.impl.component;

import com.pdev.user_service.controller.response.PageResponse;
import com.pdev.user_service.dto.component.ComponentRequestDTO;
import com.pdev.user_service.exception.RecordNotFoundException;
import com.pdev.user_service.mapper.applicationScope.ApplicationScopeMapper;
import com.pdev.user_service.mapper.component.ComponentMapper;
import com.pdev.user_service.mapper.module.ModuleMapper;
import com.pdev.user_service.model.AuditData;
import com.pdev.user_service.model.applicationScope.ApplicationScope;
import com.pdev.user_service.model.component.Component;
import com.pdev.user_service.model.module.Module;
import com.pdev.user_service.repository.applicationScope.ApplicationScopeRepository;
import com.pdev.user_service.repository.component.ComponentRepository;
import com.pdev.user_service.repository.module.ModuleRepository;
import com.pdev.user_service.service.component.ComponentService;
import com.pdev.user_service.service.validation.component.ValidateComponent;
import com.pdev.user_service.util.CommonResponse;
import com.pdev.user_service.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author @maleeshasa
 * @Date 2024/02/22
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ComponentServiceImpl implements ComponentService {

    private final ValidateComponent validateComponent;
    private final ApplicationScopeRepository applicationScopeRepository;
    private final ModuleRepository moduleRepository;
    private final ComponentRepository componentRepository;
    private final CommonUtil commonUtil;
    private final ComponentMapper componentMapper;
    private final ApplicationScopeMapper applicationScopeMapper;
    private final ModuleMapper moduleMapper;

    /**
     * This method is allowed to create or update component
     *
     * @param componentRequest {@link ComponentRequestDTO} - component request
     * @return {@link CommonResponse} - component created or updated response
     * @author @maleeshasa
     */
    @Override
    public CommonResponse createOrUpdate(ComponentRequestDTO componentRequest) {
        log.info("ComponentServiceImpl.createOrUpdate() => started.");
        // Validate component
        validateComponent.validateComponent(componentRequest);

        List<Component> mappedComponents = new ArrayList<>();
        Module module = moduleRepository.findById(componentRequest.getModule()).orElseThrow(() -> new RecordNotFoundException("Module is not exists."));
        ApplicationScope applicationScope = applicationScopeRepository.findByUniqueId(componentRequest.getApplicationScope());
        String message;
        if (componentRequest.getComponent() != null) {
            message = "Component is updated.";
            Component component = componentRepository.findById(componentRequest.getComponent())
                    .orElseThrow(() -> new RecordNotFoundException("Component is not exists."));
            component.getAuditData().setUpdatedBy(commonUtil.getUsername());
            component.getAuditData().setUpdatedOn(LocalDateTime.now());
            Component mappedComponent = componentMapper.mapToEntity(component, componentRequest);
            mappedComponent.setApplicationScope(applicationScope);
            mappedComponent.setModule(module);
            mappedComponents.add(mappedComponent);

        } else {
            message = "Components are created.";
            componentRequest.getComponents().forEach(c -> {
                Component mappedComponent = componentMapper.mapToEntity(new Component(), new ComponentRequestDTO(c.getKey(), c.getName(), c.getStatus()));
                mappedComponent.setApplicationScope(applicationScope);
                mappedComponent.setModule(module);
                mappedComponent.setAuditData(new AuditData(LocalDateTime.now(), commonUtil.getUsername()));
                mappedComponents.add(mappedComponent);
            });
        }

        CommonResponse commonResponse = new CommonResponse();
        try {
            commonResponse.setData(componentMapper.mapToList(componentRepository.saveAll(mappedComponents)));
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setMessage(message);
            log.info("ComponentServiceImpl.createOrUpdate() => ended.");
            return commonResponse;

        } catch (Exception e) {
            log.error("Error while saving components. Error: {}", e.getMessage());
            commonResponse.setData(null);
            commonResponse.setMessage("Components save failed.");
            commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return commonResponse;
        }
    }

    /**
     * This method is allowed to get all components
     *
     * @return {@link ResponseEntity <CommonResponse>} - all components
     * @author @maleeshasa
     */
    @Override
    public CommonResponse getAll() {
        log.info("ComponentServiceImpl.getAll() => started.");
        List<Component> components = componentRepository.findAll().stream()
                .filter(Component::getActive).toList();
        CommonResponse commonResponse = new CommonResponse();
        if (!components.isEmpty()) {
            log.info("Components are exists.");
            commonResponse.setData(componentMapper.mapToList(components));
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setMessage("Components are exists.");
            return commonResponse;

        } else {
            log.info("Components are not exists.");
            commonResponse.setData(null);
            commonResponse.setStatus(HttpStatus.NO_CONTENT);
            commonResponse.setMessage("Components are not exists.");
            return commonResponse;
        }
    }

    /**
     * This method is allowed to get all components
     *
     * @param pageRequest {@link PageRequest} - page request
     * @return {@link ResponseEntity <CommonResponse>} - all components
     * @author @maleeshasa
     */
    @Override
    public CommonResponse getAllWithPage(PageRequest pageRequest) {
        log.info("ComponentServiceImpl.getAllWithPage() => started.");
        Page<Component> componentsPage = componentRepository.findAll(pageRequest);
        CommonResponse commonResponse = new CommonResponse();
        if (!componentsPage.isEmpty()) {
            log.info("Components are available.");
            // Constructing page response as pagination
            PageResponse pageResponse = PageResponse.builder()
                    .totalPages(componentsPage.getTotalPages())
                    .totalElements(componentsPage.getTotalElements())
                    .currentPage(componentsPage.getNumber())
                    .dataList(componentMapper.mapToList(componentsPage.getContent())).build();

            commonResponse.setData(pageResponse);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setMessage("Components are exists.");
            return commonResponse;

        } else {
            log.info("Components are not available.");
            commonResponse.setData(null);
            commonResponse.setStatus(HttpStatus.NO_CONTENT);
            commonResponse.setMessage("Components are not exists.");
            return commonResponse;
        }
    }

    /**
     * This method is allowed to get all components by scope and module
     *
     * @param scope    {@link String} - scope uuid
     * @param moduleId {@link int} - module id
     * @return {@link CommonResponse} - all components by scope and module
     * @author @maleeshasa
     */
    @Override
    public CommonResponse getByScopeAndModule(String scope, int moduleId) {
        log.info("ComponentServiceImpl.getByScopeAndModule() => started.");
        List<Component> components = componentRepository.findByApplicationScopeUniqueIdAndModuleId(scope, moduleId);
        if (!components.isEmpty()) {
            log.info("Components are exists for scope and module.");
            return new CommonResponse(HttpStatus.OK, "Components are exists.", componentMapper.mapToList(components));
        } else {
            log.info("Components are not exists for scope and module.");
            return new CommonResponse(HttpStatus.NO_CONTENT, "Components are not exists.", null);
        }
    }

    @Override
    public CommonResponse deleteById(int id) {
        Component component = componentRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Component not found."));
        component.setActive(Boolean.FALSE);
        componentRepository.save(component);
        return new CommonResponse(
                HttpStatus.OK, "Component is deleted.", null
        );
    }
}
