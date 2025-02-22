package com.pdev.user_service.repository.component;

import com.pdev.user_service.model.component.Component;
import com.pdev.user_service.model.module.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Repository
public interface ComponentRepository extends JpaRepository<Component, Integer> {

    Component findByNameIgnoreCase(String key);

    Component findByElementNameIgnoreCase(String component);
}
