package com.pdev.user_service.service.mfa;

import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.dto.user.userDetails.UserDetailsRequestDTO;
import com.pdev.user_service.util.CommonResponse;

public interface MFARegistryService {

    void saveMFARegistry(UserRequestDTO userRequest);

    CommonResponse mfaStatus(UserDetailsRequestDTO userDetailsRequest);
}
