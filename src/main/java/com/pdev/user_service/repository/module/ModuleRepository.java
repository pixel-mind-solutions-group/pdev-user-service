package com.pdev.user_service.repository.module;

import com.pdev.user_service.model.module.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Repository
public interface ModuleRepository extends JpaRepository<Module, Integer> {

    Module findByNameIgnoreCase(String key);

    Module findByElementNameIgnoreCase(String name);

    List<Module> findByApplicationScopeUniqueId(String uuid);

    Module findByIdAndActive(Integer id, Boolean active);
}
