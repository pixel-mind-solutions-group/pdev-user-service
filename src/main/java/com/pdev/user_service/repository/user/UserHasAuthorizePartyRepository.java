package com.pdev.user_service.repository.user;

import com.pdev.user_service.model.user.UserHasAuthorizeParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Repository
public interface UserHasAuthorizePartyRepository extends JpaRepository<UserHasAuthorizeParty, Integer> {
}
