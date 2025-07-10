package com.pdev.user_service.service.user.loginRegistry;

import com.pdev.user_service.dto.user.UserLoginRegistryRequestDTO;

public interface UserLoginRegistryService {

    void saveLoginRegistry(UserLoginRegistryRequestDTO request);
}
