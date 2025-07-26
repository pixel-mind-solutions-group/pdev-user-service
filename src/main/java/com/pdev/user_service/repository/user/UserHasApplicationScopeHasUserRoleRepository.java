package com.pdev.user_service.repository.user;

import com.pdev.user_service.model.applicationScope.ApplicationScope;
import com.pdev.user_service.model.user.external.pixelHR.PixelHRUser;
import com.pdev.user_service.model.user.internal.User;
import com.pdev.user_service.model.user.UserHasApplicationScopeHasUserRole;
import com.pdev.user_service.model.userRole.UserRole;
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

    List<UserHasApplicationScopeHasUserRole> findByPixelHRUserAndApplicationScopeAndActiveTrue(PixelHRUser user, ApplicationScope applicationScope);

    List<UserHasApplicationScopeHasUserRole> findByUserRole(UserRole role);
}
