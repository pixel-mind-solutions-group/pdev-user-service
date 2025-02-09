package com.pdev.user_service.repository.userRole;

import com.pdev.user_service.model.applicationScope.ApplicationScope;
import com.pdev.user_service.model.userRole.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    Optional<UserRole> findByRole(String userRole);
    Optional<UserRole> findByRoleAndApplicationScope(String userRole, ApplicationScope applicationScope);
}
