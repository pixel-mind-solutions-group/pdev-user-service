package com.pdev.user_service.service.mfa;

import com.pdev.user_service.dto.user.UserRequestDTO;

public interface MFARegistryService {

    void saveMFARegistry(UserRequestDTO userRequest);
}
