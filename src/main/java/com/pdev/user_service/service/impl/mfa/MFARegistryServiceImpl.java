package com.pdev.user_service.service.impl.mfa;

import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.dto.user.userDetails.UserDetailsRequestDTO;
import com.pdev.user_service.dto.user.userDetails.UserDetailsResponseDTO;
import com.pdev.user_service.mapper.mfa.MFARegistryMapper;
import com.pdev.user_service.model.applicationScope.ApplicationScope;
import com.pdev.user_service.model.mfa.MFARegistry;
import com.pdev.user_service.model.user.external.pixelHR.PixelHRUser;
import com.pdev.user_service.repository.applicationScope.ApplicationScopeRepository;
import com.pdev.user_service.repository.mfa.MFARegistryRepository;
import com.pdev.user_service.repository.user.external.pixelHR.PixelHRUserRepository;
import com.pdev.user_service.service.mfa.MFARegistryService;
import com.pdev.user_service.service.validation.user.ValidateUser;
import com.pdev.user_service.util.CommonResponse;
import com.pdev.user_service.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MFARegistryServiceImpl implements MFARegistryService {

    private final MFARegistryRepository mfaRegistryRepository;
    private final ApplicationScopeRepository applicationScopeRepository;
    private final PixelHRUserRepository pixelHRUserRepository;
    private final CommonUtil commonUtil;
    private final ValidateUser validateUser;
    private final MFARegistryMapper mfaRegistryMapper;

    @Override
    public void saveMFARegistry(UserRequestDTO userRequest) {
        MFARegistry mfaRegistry = new MFARegistry();
        mfaRegistryRepository.save(mfaRegistryMapper.mapToEntity(mfaRegistry, userRequest));
    }

    @Override
    public CommonResponse mfaStatus(UserDetailsRequestDTO userDetailsRequest) {

        // Get the current username from security context
        String username = commonUtil.getUsername();
        PixelHRUser user = pixelHRUserRepository.findByUserName(username);
        ApplicationScope applicationScope = applicationScopeRepository.findByUniqueId(userDetailsRequest.getUuid());
        MFARegistry mfaRegistry = mfaRegistryRepository.findByApplicationScopeAndRefValue(applicationScope, username);

        validateUser.validateExternalUser(user);
        validateUser.validateExternalUserApplicationScope(user, applicationScope);

        UserDetailsResponseDTO response = new UserDetailsResponseDTO();
        response.setMfaStatus(mfaRegistry.getMfaStatus());

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(response);
        commonResponse.setStatus(HttpStatus.OK);
        commonResponse.setMessage("User details are exists.");
        return commonResponse;
    }
}
