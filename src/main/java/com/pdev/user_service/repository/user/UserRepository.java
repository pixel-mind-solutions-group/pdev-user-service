package com.pdev.user_service.repository.user;

import com.pdev.user_service.model.user.internal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserName(String username);

    User findByEmail(String email);
}
