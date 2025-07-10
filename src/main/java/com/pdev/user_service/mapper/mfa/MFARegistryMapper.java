package com.pdev.user_service.mapper.mfa;

import com.pdev.user_service.constant.CommonConstants;
import com.pdev.user_service.constant.PixelHRConstants;
import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.enums.MFAStatus;
import com.pdev.user_service.exception.RecordNotFoundException;
import com.pdev.user_service.model.mfa.MFARegistry;
import com.pdev.user_service.repository.applicationScope.ApplicationScopeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class MFARegistryMapper {

    private final ApplicationScopeRepository applicationScopeRepository;

    public MFARegistry mapToEntity(MFARegistry mfaRegistry, UserRequestDTO dto) {
        mfaRegistry.setMfaStatus(MFAStatus.INITIATED.getValue());
        mfaRegistry.setRemark(CommonConstants.PIXEL_HR_MFA_REMARK);
        mfaRegistry.setRefValue(dto.getUserName());
        mfaRegistry.setCreatedDate(LocalDateTime.now());
        mfaRegistry.setApplicationScope(
                applicationScopeRepository.findByScope(PixelHRConstants.PIXEL_HR_APP_SCOPE)
                        .orElseThrow(() -> new RecordNotFoundException("App scope not foud."))
        );
        return mfaRegistry;
    }
}
