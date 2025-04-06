package com.pdev.user_service.service.impl.componentElement;

import com.pdev.user_service.controller.response.PageResponse;
import com.pdev.user_service.dto.component.ComponentRequestDTO;
import com.pdev.user_service.dto.component.ComponentResponseDTO;
import com.pdev.user_service.dto.componentElement.ComponentElementRequestDTO;
import com.pdev.user_service.dto.componentElement.ComponentElementResponseDTO;
import com.pdev.user_service.dto.componentElement.ComponentElementsByComponentResponseDTO;
import com.pdev.user_service.exception.RecordNotFoundException;
import com.pdev.user_service.mapper.component.ComponentMapper;
import com.pdev.user_service.mapper.componentElement.ComponentElementMapper;
import com.pdev.user_service.model.AuditData;
import com.pdev.user_service.model.applicationScope.ApplicationScope;
import com.pdev.user_service.model.component.Component;
import com.pdev.user_service.model.componentElement.ComponentElement;
import com.pdev.user_service.model.module.Module;
import com.pdev.user_service.repository.applicationScope.ApplicationScopeRepository;
import com.pdev.user_service.repository.component.ComponentRepository;
import com.pdev.user_service.repository.componentElement.ComponentElementRepository;
import com.pdev.user_service.repository.module.ModuleRepository;
import com.pdev.user_service.service.componentElement.ComponentElementService;
import com.pdev.user_service.service.validation.componentElement.ValidateComponentElement;
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
 * @Date 2025/02/22
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ComponentElementServiceImpl implements ComponentElementService {

    private final ComponentElementRepository componentElementRepository;
    private final ApplicationScopeRepository applicationScopeRepository;
    private final ModuleRepository moduleRepository;
    private final ComponentRepository componentRepository;
    private final ValidateComponentElement validateComponentElement;
    private final ComponentElementMapper componentElementMapper;
    private final ComponentMapper componentMapper;
    private final CommonUtil commonUtil;

    /**
     * This method is allowed to create or update component element
     *
     * @param componentElementRequest {@link ComponentRequestDTO} - component element request
     * @return {@link ResponseEntity <CommonResponse>} - component element created or updated response
     * @author @maleeshasa
     */
    @Override
    public CommonResponse createOrUpdate(ComponentElementRequestDTO componentElementRequest) {
        log.info("ComponentElementServiceImpl.createOrUpdate() => started.");
        // Validate component
        validateComponentElement.validateComponentElement(componentElementRequest);

        List<ComponentElement> mappedComponentElements = new ArrayList<>();
        Module module = moduleRepository.findById(componentElementRequest.getModule()).orElseThrow(() -> new RecordNotFoundException("Module is not exists."));
        ApplicationScope applicationScope = applicationScopeRepository.findByUniqueId(componentElementRequest.getApplicationScope());
        Component component = componentRepository.findById(componentElementRequest.getComponent()).orElseThrow(() -> new RecordNotFoundException("Component is not exists."));
        String message;
        if (componentElementRequest.getComponentElementId() != null) {
            message = "Component element is updated.";
            ComponentElement componentElement = componentElementRepository.findById(componentElementRequest.getComponentElementId())
                    .orElseThrow(() -> new RecordNotFoundException("Component element is not exists."));
            componentElement.getAuditData().setUpdatedBy(commonUtil.getUsername());
            componentElement.getAuditData().setUpdatedOn(LocalDateTime.now());
            ComponentElement mappedComponentElement = componentElementMapper.mapToEntity(componentElement, componentElementRequest);
            mappedComponentElement.setApplicationScope(applicationScope);
            mappedComponentElement.setModule(module);
            mappedComponentElement.setComponent(component);
            mappedComponentElements.add(mappedComponentElement);

        } else {
            message = "Component elements are created.";
            componentElementRequest.getComponentElements().forEach(c -> {
                ComponentElement mappedComponentElement = componentElementMapper.mapToEntity(new ComponentElement(), new ComponentElementRequestDTO(c.getKey(), c.getName(), c.getStatus()));
                mappedComponentElement.setApplicationScope(applicationScope);
                mappedComponentElement.setModule(module);
                mappedComponentElement.setComponent(component);
                mappedComponentElement.setAuditData(new AuditData(LocalDateTime.now(), commonUtil.getUsername()));
                mappedComponentElements.add(mappedComponentElement);
            });
        }

        CommonResponse commonResponse = new CommonResponse();
        try {
            commonResponse.setData(componentElementMapper.mapToList(componentElementRepository.saveAll(mappedComponentElements)));
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setMessage(message);
            log.info("ComponentElementServiceImpl.createOrUpdate() => ended.");
            return commonResponse;

        } catch (Exception e) {
            log.error("Error while saving component elements. Error: {}", e.getMessage());
            commonResponse.setData(null);
            commonResponse.setMessage("Component elements save failed.");
            commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return commonResponse;
        }
    }

    /**
     * This method is allowed to get all component elements
     *
     * @return {@link ResponseEntity <CommonResponse>} - all components elements
     * @author @maleeshasa
     */
    @Override
    public CommonResponse getAll() {
        log.info("ComponentElementServiceImpl.getAll() => started.");
        List<ComponentElement> componentElements = componentElementRepository.findAll().stream()
                .filter(ComponentElement::getActive)
                .toList();
        CommonResponse commonResponse = new CommonResponse();
        if (!componentElements.isEmpty()) {
            log.info("Component elements are exists.");
            commonResponse.setData(componentElementMapper.mapToList(componentElements));
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setMessage("Component elements are exists.");
            return commonResponse;

        } else {
            log.info("Component elements are not exists.");
            commonResponse.setData(null);
            commonResponse.setStatus(HttpStatus.NO_CONTENT);
            commonResponse.setMessage("Component elements are not exists.");
            return commonResponse;
        }
    }

    /**
     * This method is allowed to get all component elements with pagination
     *
     * @param pageRequest {@link PageRequest} - page request
     * @return {@link CommonResponse} - all components elements
     * @author @maleeshasa
     */
    @Override
    public CommonResponse getAllWithPage(PageRequest pageRequest) {
        log.info("ComponentElementServiceImpl.getAllWithPage() => started.");
        Page<ComponentElement> componentElements = componentElementRepository.findAll(pageRequest);
        CommonResponse commonResponse = new CommonResponse();
        if (!componentElements.isEmpty()) {
            log.info("Component elements are available.");
            // Constructing page response as pagination
            PageResponse pageResponse = PageResponse.builder()
                    .totalPages(componentElements.getTotalPages())
                    .totalElements(componentElements.getTotalElements())
                    .currentPage(componentElements.getNumber())
                    .dataList(componentElementMapper.mapToList(componentElements.getContent())).build();

            commonResponse.setData(pageResponse);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setMessage("Component elements are exists.");
            return commonResponse;

        } else {
            log.info("Component elements are not available.");
            commonResponse.setData(null);
            commonResponse.setStatus(HttpStatus.NO_CONTENT);
            commonResponse.setMessage("Component elements are not exists.");
            return commonResponse;
        }
    }

    @Override
    public CommonResponse getById(Integer id) {
        ComponentElement componentElement = componentElementRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Component element not found."));
        return new CommonResponse(
                HttpStatus.OK,
                "Component element is exists.",
                componentElementMapper.mapToDTO(new ComponentElementResponseDTO(), componentElement)
        );
    }

    @Override
    public CommonResponse deleteById(int id) {
        ComponentElement componentElement = componentElementRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Component element not found."));
        componentElementRepository.delete(componentElement);
        return new CommonResponse(
                HttpStatus.OK, "Component element is deleted.", null
        );
    }

    @Override
    public CommonResponse getByScopeAndComponents(String scope, List<Integer> components) {
        log.info("ComponentElementServiceImpl.getByScopeAndComponents() => started.");
        List<ComponentElementsByComponentResponseDTO> list = new ArrayList<>();
        for (Integer component : components) {
            ApplicationScope applicationScope = applicationScopeRepository.findByUniqueId(scope);
            Component componentObj = componentRepository.findById(component).orElseThrow(() -> new RecordNotFoundException("Component is not exists."));
            List<ComponentElement> componentElements = componentElementRepository.findByApplicationScopeAndComponent(applicationScope, componentObj);
            if (!componentElements.isEmpty()) {
                ComponentElementsByComponentResponseDTO response = new ComponentElementsByComponentResponseDTO();
                response.setComponent(componentMapper.mapToDTO(new ComponentResponseDTO(), componentObj));
                response.setElements(componentElementMapper.mapToList(componentElements));
                list.add(response);
            }
        }
        if (!list.isEmpty()) {
            log.info("Component elements are exists for scope and modules.");
            return new CommonResponse(HttpStatus.OK, "Component elements are exists.", list);
        } else {
            log.info("Component elements are not exists for scope and modules.");
            return new CommonResponse(HttpStatus.NO_CONTENT, "Component elements are not exists.", null);
        }
    }
}
