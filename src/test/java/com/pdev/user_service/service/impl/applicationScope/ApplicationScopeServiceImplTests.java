package com.pdev.user_service.service.impl.applicationScope;

import com.pdev.user_service.dto.applicationScope.ApplicationScopeRequestDTO;
import com.pdev.user_service.service.applicationScope.ApplicationScopeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApplicationScopeServiceImplTests {

    @Autowired
    ApplicationScopeService applicationScopeService;

    @Test
    void createOrUpdateApplicationScopeSuccess() {
        ApplicationScopeRequestDTO requestDTO = new ApplicationScopeRequestDTO();
        requestDTO.setApplicationScopeId(null);
        requestDTO.setScope("dsadas");
        requestDTO.setStatus("Active");
        requestDTO.setUniqueId(null);
        applicationScopeService.createOrUpdateApplicationScope(requestDTO);
    }
}