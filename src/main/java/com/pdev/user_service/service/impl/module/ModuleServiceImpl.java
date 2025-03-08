package com.pdev.user_service.service.impl.module;

import com.pdev.user_service.controller.response.PageResponse;
import com.pdev.user_service.dto.applicationScope.ApplicationScopeResponseDTO;
import com.pdev.user_service.dto.module.ModuleRequestDTO;
import com.pdev.user_service.dto.module.ModuleResponseDTO;
import com.pdev.user_service.exception.RecordNotFoundException;
import com.pdev.user_service.mapper.module.ModuleMapper;
import com.pdev.user_service.model.AuditData;
import com.pdev.user_service.model.applicationScope.ApplicationScope;
import com.pdev.user_service.model.module.Module;
import com.pdev.user_service.repository.applicationScope.ApplicationScopeRepository;
import com.pdev.user_service.repository.module.ModuleRepository;
import com.pdev.user_service.service.module.ModuleService;
import com.pdev.user_service.service.validation.module.ValidateModule;
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
public class ModuleServiceImpl implements ModuleService {

    private final ModuleMapper moduleMapper;
    private final CommonUtil commonUtil;
    private final ValidateModule validateModule;
    private final ModuleRepository moduleRepository;
    private final ApplicationScopeRepository applicationScopeRepository;

    /**
     * This method is allowed to create or update module
     *
     * @param moduleRequest {@link ModuleRequestDTO} - module request
     * @return {@link CommonResponse} - module created or updated response
     * @author @maleeshasa
     */
    @Override
    public CommonResponse createOrUpdate(ModuleRequestDTO moduleRequest) {
        log.info("ModuleServiceImpl.createOrUpdate() => started.");

        // Validate module
        validateModule.validateModule(moduleRequest);

        List<Module> mappedModules = new ArrayList<>();
        ApplicationScope applicationScope = applicationScopeRepository.findByUniqueId(moduleRequest.getApplicationScope());
        String message;
        if (moduleRequest.getId() != null) {
            message = "Module is updated.";
            Module module = moduleRepository.findById(moduleRequest.getId())
                    .orElseThrow(() -> new RecordNotFoundException("Module is not exists."));
            module.getAuditData().setUpdatedBy(commonUtil.getUsername());
            module.getAuditData().setUpdatedOn(LocalDateTime.now());
            Module mappedModule = moduleMapper.mapToEntity(module, moduleRequest);
            mappedModule.setApplicationScope(applicationScope);
            mappedModules.add(mappedModule);

        } else {
            message = "Modules are created.";
            moduleRequest.getModules().forEach(m -> {
                Module mappedModule = moduleMapper.mapToEntity(new Module(), new ModuleRequestDTO(m.getKey(), m.getModule(), m.getStatus()));
                mappedModule.setApplicationScope(applicationScope);
                mappedModule.setAuditData(new AuditData(LocalDateTime.now(), commonUtil.getUsername()));
                mappedModules.add(mappedModule);
            });
        }

        CommonResponse commonResponse = new CommonResponse();
        try {
            commonResponse.setData(moduleMapper.mapToList(moduleRepository.saveAll(mappedModules)));
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setMessage(message);
            log.info("ModuleServiceImpl.createOrUpdate() => ended.");
            return commonResponse;

        } catch (Exception e) {
            log.error("Error while saving modules. Error: {}", e.getMessage());
            commonResponse.setData(null);
            commonResponse.setMessage("Modules save failed.");
            commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return commonResponse;
        }
    }

    /**
     * This method is allowed to get all modules
     *
     * @return {@link ResponseEntity <CommonResponse>} - all modules
     * @author @maleeshasa
     */
    @Override
    public CommonResponse getAll() {
        log.info("ModuleServiceImpl.getAll() => started.");
        List<Module> modules = moduleRepository.findAll();
        CommonResponse commonResponse = new CommonResponse();
        if (!modules.isEmpty()) {
            log.info("Modules are exists.");
            commonResponse.setData(moduleMapper.mapToList(modules));
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setMessage("Modules are exists.");
            return commonResponse;

        } else {
            log.info("Modules are not available.");
            commonResponse.setData(null);
            commonResponse.setStatus(HttpStatus.NO_CONTENT);
            commonResponse.setMessage("Modules are not exists.");
            return commonResponse;
        }
    }

    /**
     * This method is allowed to get all modules with pagination
     *
     * @param pageRequest {@link PageRequest} - page request
     * @return {@link ResponseEntity <CommonResponse>} - all modules
     * @author @maleeshasa
     */
    @Override
    public CommonResponse getAllWithPagination(PageRequest pageRequest) {
        log.info("ModuleServiceImpl.getAllWithPagination() => started.");
        Page<Module> modulesPage = moduleRepository.findAll(pageRequest);
        CommonResponse commonResponse = new CommonResponse();
        if (!modulesPage.isEmpty()) {
            log.info("Modules are available.");
            // Constructing page response as pagination
            PageResponse pageResponse = PageResponse.builder()
                    .totalPages(modulesPage.getTotalPages())
                    .totalElements(modulesPage.getTotalElements())
                    .currentPage(modulesPage.getNumber())
                    .dataList(moduleMapper.mapToList(modulesPage.getContent())).build();

            commonResponse.setData(pageResponse);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setMessage("Modules are exists.");
            return commonResponse;

        } else {
            log.info("Modules are not exists.");
            commonResponse.setData(null);
            commonResponse.setStatus(HttpStatus.NO_CONTENT);
            commonResponse.setMessage("Modules are not exists.");
            return commonResponse;
        }
    }

    /**
     * This method is allowed to get modules by application scope
     *
     * @param uuid {@link String} - application scope uuid
     * @return {@link CommonResponse} - modules by application scope response
     * @author @maleeshasa
     */
    @Override
    public CommonResponse getModulesByAppScope(String uuid) {
        log.info("ModuleServiceImpl.getModulesByAppScope() => started.");
        CommonResponse commonResponse = new CommonResponse();
        List<Module> modules = moduleRepository.findByApplicationScopeUniqueId(uuid).stream()
                .filter(Module::getActive)
                .toList();
        if (!modules.isEmpty()) {
            log.info("Modules are exists by app scope.");
            commonResponse.setData(moduleMapper.mapToList(modules));
            commonResponse.setMessage("Modules are exists.");
            commonResponse.setStatus(HttpStatus.OK);
            return commonResponse;
        } else {
            log.info("Modules are not exists by app scope.");
            commonResponse.setData(null);
            commonResponse.setMessage("Modules are not exists.");
            commonResponse.setStatus(HttpStatus.NO_CONTENT);
            return commonResponse;
        }
    }

    @Override
    public CommonResponse getById(Integer id) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Module not found."));
        return new CommonResponse(
                HttpStatus.OK,
                "Module is exists.",
                moduleMapper.mapToDTO(new ModuleResponseDTO(), module)
        );
    }

    @Override
    public CommonResponse deleteById(int id) {
        Module module = moduleRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Module not found."));
        moduleRepository.delete(module);
        return new CommonResponse(
                HttpStatus.OK, "Module is deleted.", null
        );
    }
}
