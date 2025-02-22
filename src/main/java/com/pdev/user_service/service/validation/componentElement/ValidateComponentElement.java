package com.pdev.user_service.service.validation.componentElement;

import com.pdev.user_service.dto.componentElement.ComponentElementRequestDTO;
import com.pdev.user_service.exception.BaseException;
import com.pdev.user_service.exception.RecordNotFoundException;
import com.pdev.user_service.model.applicationScope.ApplicationScope;
import com.pdev.user_service.model.componentElement.ComponentElement;
import com.pdev.user_service.repository.applicationScope.ApplicationScopeRepository;
import com.pdev.user_service.repository.componentElement.ComponentElementRepository;
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
public class ValidateComponentElement {

    private final ApplicationScopeRepository applicationScopeRepository;
    private final ComponentElementRepository componentElementRepository;

    public void validateComponentElement(ComponentElementRequestDTO componentElementRequest) {
        log.info("ValidateComponentElement.validateComponentElement() => started.");
        if (CommonValidation.stringNullValidation(componentElementRequest.getApplicationScope())) {
            throw new RecordNotFoundException("Application scope is required.");
        }
        ApplicationScope applicationScope = applicationScopeRepository.findByUniqueId(componentElementRequest.getApplicationScope());
        if (applicationScope == null) {
            throw new RecordNotFoundException("Application scope is not exists.");
        }

        AtomicBoolean isCreation = new AtomicBoolean();
        if (componentElementRequest.getComponentElementId() != null) {
            isCreation.set(false);
            if (CommonValidation.stringNullValidation(componentElementRequest.getName())) {
                throw new RecordNotFoundException("Component element is required.");

            } else if (CommonValidation.stringNullValidation(componentElementRequest.getKey())) {
                throw new RecordNotFoundException("Key is required.");

            } else if (CommonValidation.stringNullValidation(componentElementRequest.getStatus())) {
                throw new RecordNotFoundException("Status is required.");
            }
            boolean valid = CommonValidation.validStatus(componentElementRequest.getStatus());
            if (valid == Boolean.FALSE) {
                log.warn("Status is invalid.");
                throw new BaseException(422, "Status is invalid.");
            }

            // Validate update component element request component and key
            validateComponentElementNameAndKey(componentElementRequest.getName(), componentElementRequest.getKey(), isCreation, componentElementRequest.getComponentElementId());

        } else {
            isCreation.set(true);
            componentElementRequest.getComponentElements().forEach(c -> {
                boolean valid = CommonValidation.validStatus(c.getStatus());
                if (valid == Boolean.FALSE) {
                    log.warn("Status is invalid of {}", c.getName());
                    throw new BaseException(422, "Status is invalid of " + c.getName());
                }

                if (CommonValidation.stringNullValidation(c.getName())) {
                    throw new RecordNotFoundException("Component element is required.");

                } else if (CommonValidation.stringNullValidation(c.getKey())) {
                    throw new RecordNotFoundException("Key is required.");

                } else if (CommonValidation.stringNullValidation(c.getStatus())) {
                    throw new RecordNotFoundException("Status is required.");
                }

                // Validate create component element request component and key
                validateComponentElementNameAndKey(c.getName(), c.getKey(), isCreation, null);
            });

            // must not be duplicate
            List<String> stringList = new ArrayList<>();
            componentElementRequest.getComponentElements().forEach(c -> {
                stringList.add(c.getName().toLowerCase());
                stringList.add(c.getKey().toLowerCase());
            });
            Set<String> stringSet = new HashSet<>(stringList);
            int listLength = stringList.size();
            int setLength = stringSet.size();
            if (listLength > setLength) {
                throw new BaseException(422, "Duplicate keys or names must not be entered.");
            }
            log.info("ValidateComponentElement.validateComponentElement() => ended.");
        }
    }

    private void validateComponentElementNameAndKey(String componentElement, String key, AtomicBoolean isCreation, Integer componentElementId) {
        log.info("ValidateComponentElement.validateComponentElementNameAndKey() => started.");
        ComponentElement byKeyName = componentElementRepository.findByNameIgnoreCase(key);
        ComponentElement byComponentElementName = componentElementRepository.findByElementNameIgnoreCase(componentElement);

        if (key.equalsIgnoreCase(componentElement)) {
            throw new BaseException(422, "Both key and name must not be same." + key + " : " + componentElement);
        }

        if (isCreation.get()) {
            if (byKeyName != null) {
                throw new BaseException(422, "Entered key is already in used: " + byKeyName.getName());

            } else if (byComponentElementName != null) {
                throw new BaseException(422, "Entered element name is already in used: " + byComponentElementName.getElementName());
            }

        } else {
            ComponentElement byId = componentElementRepository.findById(componentElementId).orElseThrow(() -> new RecordNotFoundException("Component element is not exists."));
            if (!byId.getName().equalsIgnoreCase(key)) {
                if (byKeyName != null) {
                    throw new BaseException(422, "Entered key is already in used for another component element: " + byKeyName.getName());
                }
            }

            if (!byId.getElementName().equalsIgnoreCase(componentElement)) {
                if (byComponentElementName != null) {
                    throw new BaseException(422, "Entered element name is already in used for another component element: " + byComponentElementName.getElementName());
                }
            }
        }
        log.info("ValidateComponentElement.validateComponentElementNameAndKey() => ended.");
    }
}
