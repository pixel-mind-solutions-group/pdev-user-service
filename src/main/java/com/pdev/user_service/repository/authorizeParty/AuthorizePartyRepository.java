package com.pdev.user_service.repository.authorizeParty;

import com.pdev.user_service.model.authorizeParty.AuthorizeParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Repository
public interface AuthorizePartyRepository extends JpaRepository<AuthorizeParty, Integer> {
}
