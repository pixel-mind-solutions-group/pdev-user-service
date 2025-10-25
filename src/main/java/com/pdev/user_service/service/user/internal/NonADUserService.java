package com.pdev.user_service.service.user.internal;

import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.util.CommonResponse;

/**
 * @author maleeshasa
 * @Date 2024/11/15
 * @deprecated
 */
public interface NonADUserService {

    /**
     * This method is allowed to create or modify Non AD user
     *
     * @param userRequest {@link UserRequestDTO} - Non AD user request details
     * @return {@link CommonResponse} - Non AD user created or modified response
     * @author maleesahsa
     */
    CommonResponse createNonAD(UserRequestDTO userRequest);
}
