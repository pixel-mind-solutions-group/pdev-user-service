package com.pdev.user_service.repository.user;

import com.pdev.user_service.model.user.UserLoginRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginRegistryRepository extends JpaRepository<UserLoginRegistry, Integer> {
}
