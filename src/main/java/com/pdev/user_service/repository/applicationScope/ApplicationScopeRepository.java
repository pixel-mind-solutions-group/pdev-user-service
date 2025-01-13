package com.pdev.user_service.repository.applicationScope;

import com.pdev.user_service.model.applicationScope.ApplicationScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Repository
public interface ApplicationScopeRepository extends JpaRepository<ApplicationScope, Integer> {

    ApplicationScope findByUniqueId(String uuid);

    Optional<ApplicationScope> findByScope(String scopeName);
}
