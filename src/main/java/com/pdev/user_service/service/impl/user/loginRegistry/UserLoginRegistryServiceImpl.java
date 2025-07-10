package com.pdev.user_service.service.impl.user.loginRegistry;

import com.pdev.user_service.dto.user.UserLoginRegistryRequestDTO;
import com.pdev.user_service.model.user.UserLoginRegistry;
import com.pdev.user_service.repository.user.UserLoginRegistryRepository;
import com.pdev.user_service.service.user.loginRegistry.UserLoginRegistryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserLoginRegistryServiceImpl implements UserLoginRegistryService {

    private final UserLoginRegistryRepository loginRegistryRepository;

    @Override
    public void saveLoginRegistry(UserLoginRegistryRequestDTO request) {
        UserLoginRegistry userLoginRegistry = new UserLoginRegistry();
        userLoginRegistry.setUser(request.getUser());
        userLoginRegistry.setLoginOrLogout(request.getIsLogin());
        userLoginRegistry.setApplicationScope(request.getApplicationScope());
        userLoginRegistry.setLastUpdatedDateTime(LocalDateTime.now());
        userLoginRegistry.setCreatedDate(LocalDateTime.now());
        loginRegistryRepository.save(userLoginRegistry);
    }
}
