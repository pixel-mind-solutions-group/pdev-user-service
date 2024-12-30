package com.pdev.user_service.repository.authorizePartyRole;

import com.pdev.user_service.model.authorizePartyRole.AuthorizePartyRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Repository
public interface AuthorizePartyRoleRepository extends JpaRepository<AuthorizePartyRole, Integer> {
}
