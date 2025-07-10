package com.pdev.user_service.service.impl.mfa;

import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.mapper.mfa.MFARegistryMapper;
import com.pdev.user_service.model.mfa.MFARegistry;
import com.pdev.user_service.repository.mfa.MFARegistryRepository;
import com.pdev.user_service.service.mfa.MFARegistryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MFARegistryServiceImpl implements MFARegistryService {

    private final MFARegistryMapper mfaRegistryMapper;
    private final MFARegistryRepository mfaRegistryRepository;

    @Override
    public void saveMFARegistry(UserRequestDTO userRequest) {
        MFARegistry mfaRegistry = new MFARegistry();
        mfaRegistryRepository.save(mfaRegistryMapper.mapToEntity(mfaRegistry, userRequest));
    }
}
