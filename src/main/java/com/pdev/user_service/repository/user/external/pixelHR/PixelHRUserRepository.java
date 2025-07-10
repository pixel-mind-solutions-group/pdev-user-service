package com.pdev.user_service.repository.user.external.pixelHR;

import com.pdev.user_service.model.user.external.pixelHR.PixelHRUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PixelHRUserRepository extends JpaRepository<PixelHRUser, Integer> {

    PixelHRUser findByUserName(String userName);
}
