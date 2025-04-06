package com.pdev.user_service.service.validation.component;

import com.pdev.user_service.dto.component.ComponentRequestDTO;
import com.pdev.user_service.exception.BaseException;
import com.pdev.user_service.exception.RecordNotFoundException;
import com.pdev.user_service.model.applicationScope.ApplicationScope;
import com.pdev.user_service.model.componentElement.ComponentElement;
import com.pdev.user_service.repository.applicationScope.ApplicationScopeRepository;
import com.pdev.user_service.repository.component.ComponentRepository;
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
public class ValidateComponent {

    private final ApplicationScopeRepository applicationScopeRepository;
    private final ComponentRepository componentRepository;
    private final ComponentElementRepository componentElementRepository;

    public void validateComponent(ComponentRequestDTO componentRequest) {
        log.info("ValidateComponent.validateComponent() => started.");
        if (CommonValidation.stringNullValidation(componentRequest.getApplicationScope())) {
            throw new RecordNotFoundException("Application scope is required.");
        }
        ApplicationScope applicationScope = applicationScopeRepository.findByUniqueId(componentRequest.getApplicationScope());
        if (applicationScope == null) {
            throw new RecordNotFoundException("Application scope is not exists.");
        }

        AtomicBoolean isCreation = new AtomicBoolean();
        if (componentRequest.getComponent() != null) {
            isCreation.set(false);
            if (CommonValidation.stringNullValidation(componentRequest.getName())) {
                throw new RecordNotFoundException("Component is required.");

            } else if (CommonValidation.stringNullValidation(componentRequest.getKey())) {
                throw new RecordNotFoundException("Key is required.");

            } else if (CommonValidation.stringNullValidation(componentRequest.getStatus())) {
                throw new RecordNotFoundException("Status is required.");
            }
            boolean valid = CommonValidation.validStatus(componentRequest.getStatus());
            if (valid == Boolean.FALSE) {
                log.warn("Status is invalid.");
                throw new BaseException(422, "Status is invalid.");
            }

            // Validate update component request component and key
            validateComponentNameAndKey(componentRequest.getName(), componentRequest.getKey(), isCreation, componentRequest.getComponent());

        } else {
            isCreation.set(true);
            componentRequest.getComponents().forEach(c -> {
                if (CommonValidation.stringNullValidation(c.getStatus())) {
                    throw new RecordNotFoundException("Status is required for " + c.getName());
                }
                boolean valid = CommonValidation.validStatus(c.getStatus());
                if (valid == Boolean.FALSE) {
                    log.warn("Status is invalid of {}", c.getName());
                    throw new BaseException(422, "Status is invalid of " + c.getName());
                }

                if (CommonValidation.stringNullValidation(c.getName())) {
                    throw new RecordNotFoundException("Component is required.");

                } else if (CommonValidation.stringNullValidation(c.getKey())) {
                    throw new RecordNotFoundException("Key is required.");

                }

                // Validate create component request component and key
                validateComponentNameAndKey(c.getName(), c.getKey(), isCreation, null);
            });

            // must not be duplicate
            List<String> stringList = new ArrayList<>();
            componentRequest.getComponents().forEach(c -> {
                stringList.add(c.getName().toLowerCase());
                stringList.add(c.getKey().toLowerCase());
            });
            Set<String> stringSet = new HashSet<>(stringList);
            int listLength = stringList.size();
            int setLength = stringSet.size();
            if (listLength > setLength) {
                throw new BaseException(422, "Duplicate keys or names must not be entered.");
            }
            log.info("ValidateComponent.validateComponent() => ended.");
        }
    }

    private void validateComponentNameAndKey(String component, String key, AtomicBoolean isCreation, Integer componentId) {
        log.info("ValidateComponent.validateComponentNameAndKey() => started.");
        com.pdev.user_service.model.component.Component byKeyName = componentRepository.findByNameIgnoreCase(key);
        com.pdev.user_service.model.component.Component byComponentName = componentRepository.findByElementNameIgnoreCase(component);

        if (key.equalsIgnoreCase(component)) {
            throw new BaseException(422, "Both key and name must not be same." + key + " : " + component);
        }

        if (isCreation.get()) {
            if (byKeyName != null) {
                throw new BaseException(422, "Entered key is already in used: " + byKeyName.getName());

            }
//            else if (byComponentName != null) {
//                throw new BaseException(422, "Entered element name is already in used: " + byComponentName.getElementName());
//            }

        } else {
            com.pdev.user_service.model.component.Component byId = componentRepository.findById(componentId).orElseThrow(() -> new RecordNotFoundException("Component is not exists."));
            if (!byId.getName().equalsIgnoreCase(key)) {
                if (byKeyName != null) {
                    throw new BaseException(422, "Entered key is already in used for another component: " + byKeyName.getName());
                }
            }

//            if (!byId.getElementName().equalsIgnoreCase(component)) {
//                if (byComponentName != null) {
//                    throw new BaseException(422, "Entered element name is already in used for another component: " + byComponentName.getElementName());
//                }
//            }
        }
        log.info("ValidateComponent.validateComponentNameAndKey() => ended.");
    }

    public void validateDeletion(com.pdev.user_service.model.component.Component component) {
        List<ComponentElement> componentElements = componentElementRepository.findByComponent(component);
        if (!componentElements.isEmpty()) {
            throw new BaseException(400, "Component is already in used.");
        }
    }
}
