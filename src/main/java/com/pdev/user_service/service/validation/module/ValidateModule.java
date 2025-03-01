package com.pdev.user_service.service.validation.module;

import com.pdev.user_service.dto.module.ModuleRequestDTO;
import com.pdev.user_service.exception.BaseException;
import com.pdev.user_service.exception.RecordNotFoundException;
import com.pdev.user_service.model.applicationScope.ApplicationScope;
import com.pdev.user_service.model.module.Module;
import com.pdev.user_service.repository.applicationScope.ApplicationScopeRepository;
import com.pdev.user_service.repository.module.ModuleRepository;
import com.pdev.user_service.service.validation.CommonValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author @maleeshasa
 * @Date 2025/02/22
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class ValidateModule {

    private final ApplicationScopeRepository applicationScopeRepository;
    private final ModuleRepository moduleRepository;

    public void validateModule(ModuleRequestDTO moduleRequest) {
        log.info("ValidateModule.validateModule() => started.");
        if (CommonValidation.stringNullValidation(moduleRequest.getApplicationScope())) {
            throw new RecordNotFoundException("Application scope is required.");
        }
        ApplicationScope applicationScope = applicationScopeRepository.findByUniqueId(moduleRequest.getApplicationScope());
        if (applicationScope == null) {
            throw new RecordNotFoundException("Application scope is not exists.");
        }

        AtomicBoolean isCreation = new AtomicBoolean();
        if (moduleRequest.getId() != null) {
            isCreation.set(false);
            if (CommonValidation.stringNullValidation(moduleRequest.getModule())) {
                throw new RecordNotFoundException("Module is required.");

            } else if (CommonValidation.stringNullValidation(moduleRequest.getKey())) {
                throw new RecordNotFoundException("Key is required.");

            } else if (CommonValidation.stringNullValidation(moduleRequest.getStatus())) {
                throw new RecordNotFoundException("Status is required.");
            }
            boolean valid = CommonValidation.validStatus(moduleRequest.getStatus());
            if (valid == Boolean.FALSE) {
                log.warn("Status is invalid.");
                throw new BaseException(422, "Status is invalid.");
            }

            // Validate update module request module and key
            validateModuleNameAndKey(moduleRequest.getModule(), moduleRequest.getKey(), isCreation, moduleRequest.getId());

        } else {
            isCreation.set(true);
            moduleRequest.getModules().forEach(m -> {
                if (CommonValidation.stringNullValidation(m.getStatus())) {
                    throw new RecordNotFoundException("Status is required.");
                }

                boolean valid = CommonValidation.validStatus(m.getStatus());

                if (valid == Boolean.FALSE) {
                    log.warn("Status is invalid of {}", m.getModule());
                    throw new BaseException(422, "Status is invalid of " + m.getModule());
                }

                if (CommonValidation.stringNullValidation(m.getModule())) {
                    throw new RecordNotFoundException("Module is required.");

                } else if (CommonValidation.stringNullValidation(m.getKey())) {
                    throw new RecordNotFoundException("Key is required.");

                }

                // Validate create module request module and key
                validateModuleNameAndKey(m.getModule(), m.getKey(), isCreation, null);
            });

            // must not be duplicate
            List<String> stringList = new ArrayList<>();
            moduleRequest.getModules().forEach(m -> {
                stringList.add(m.getModule().toLowerCase());
                stringList.add(m.getKey().toLowerCase());
            });
            Set<String> stringSet = new HashSet<>(stringList);
            int listLength = stringList.size();
            int setLength = stringSet.size();
            if (listLength > setLength) {
                throw new BaseException(422, "Duplicate keys or names must not be entered.");
            }
            log.info("ValidateModule.validateModule() => ended.");
        }
    }

    private void validateModuleNameAndKey(String module, String key, AtomicBoolean isCreation, Integer moduleId) {
        log.info("ValidateModule.validateModuleNameAndKey() => started.");
        Module byKeyName = moduleRepository.findByNameIgnoreCase(key);
        Module byModuleName = moduleRepository.findByElementNameIgnoreCase(module);

        if (key.equalsIgnoreCase(module)) {
            throw new BaseException(422, "Both key and name must not be same." + key + " : " + module);
        }

        if (isCreation.get()) {
            if (byKeyName != null) {
                throw new BaseException(422, "Entered key is already in used: " + byKeyName.getName());

            } else if (byModuleName != null) {
                throw new BaseException(422, "Entered element name is already in used: " + byModuleName.getElementName());
            }

        } else {
            Module byId = moduleRepository.findById(moduleId).orElseThrow(() -> new RecordNotFoundException("Module is not exists."));
            if (!byId.getName().equalsIgnoreCase(key)) {
                if (byKeyName != null) {
                    throw new BaseException(422, "Entered key is already in used for another module: " + byKeyName.getName());
                }
            }

            if (!byId.getElementName().equalsIgnoreCase(module)) {
                if (byModuleName != null) {
                    throw new BaseException(422, "Entered element name is already in used for another module: " + byModuleName.getElementName());
                }
            }
        }
        log.info("ValidateModule.validateModuleNameAndKey() => ended.");
    }
}
