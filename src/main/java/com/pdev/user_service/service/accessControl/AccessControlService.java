package com.pdev.user_service.service.accessControl;

import com.pdev.user_service.dto.accessControl.AccessControlRequestDTO;
import com.pdev.user_service.util.CommonResponse;

public interface AccessControlService {

    CommonResponse createOrUpdate(AccessControlRequestDTO accessControlRequest);
}
