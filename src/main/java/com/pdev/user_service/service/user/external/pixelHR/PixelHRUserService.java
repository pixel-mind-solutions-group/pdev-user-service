package com.pdev.user_service.service.user.external.pixelHR;

import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.util.CommonResponse;

public interface PixelHRUserService {

    CommonResponse createOrModify(UserRequestDTO userRequest);

    CommonResponse resetPassword(UserRequestDTO userRequest);
}
