package com.pdev.user_service.repository.userRole;

import com.pdev.user_service.model.userRole.UserRoleHasModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Repository
public interface UserRoleHasModuleRepository extends JpaRepository<UserRoleHasModule, Integer> {
}
