package com.pdev.user_service.repository.authorizeParty;

import com.pdev.user_service.model.authorizeParty.AuthorizeParty;
import com.pdev.user_service.model.authorizeParty.AuthorizePartyHasAuthorizePartyRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Repository
public interface AuthorizePartyHasAuthorizePartyRoleRepository extends JpaRepository<AuthorizePartyHasAuthorizePartyRole, Integer> {

    void deleteAllByAuthorizeParty(AuthorizeParty authorizeParty);

    List<AuthorizePartyHasAuthorizePartyRole> findByAuthorizeParty(AuthorizeParty authorizeParty);
}
