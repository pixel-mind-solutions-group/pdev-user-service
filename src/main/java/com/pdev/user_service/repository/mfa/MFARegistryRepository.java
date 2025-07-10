package com.pdev.user_service.repository.mfa;

import com.pdev.user_service.model.mfa.MFARegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MFARegistryRepository extends JpaRepository<MFARegistry, Integer> {
}
