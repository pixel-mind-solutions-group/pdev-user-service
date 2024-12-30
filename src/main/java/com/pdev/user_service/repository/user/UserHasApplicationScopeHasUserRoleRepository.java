package com.pdev.user_service.repository.user;

import com.pdev.user_service.model.applicationScope.ApplicationScope;
import com.pdev.user_service.model.user.User;
import com.pdev.user_service.model.user.UserHasApplicationScopeHasUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Repository
public interface UserHasApplicationScopeHasUserRoleRepository extends JpaRepository<UserHasApplicationScopeHasUserRole, Integer> {

    List<UserHasApplicationScopeHasUserRole> findByUserAndApplicationScopeAndActiveTrue(User user, ApplicationScope applicationScope);
}
