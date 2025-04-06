package com.pdev.user_service.repository.componentElement;

import com.pdev.user_service.model.applicationScope.ApplicationScope;
import com.pdev.user_service.model.component.Component;
import com.pdev.user_service.model.componentElement.ComponentElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Repository
public interface ComponentElementRepository extends JpaRepository<ComponentElement, Integer> {

    ComponentElement findByNameIgnoreCase(String key);

    ComponentElement findByElementNameIgnoreCase(String component);

    List<ComponentElement> findByComponent(Component component);

    List<ComponentElement> findByApplicationScopeAndComponent(ApplicationScope applicationScope, Component componentObj);
}
