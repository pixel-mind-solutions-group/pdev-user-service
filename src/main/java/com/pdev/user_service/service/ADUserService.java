package com.pdev.user_service.service;

import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.util.CommonResponse;

public interface ADUserService {

    /**
     * This method is allowed to create or modify AD user
     *
     * @param userRequest {@link UserRequestDTO} - AD user request details
     * @return {@link CommonResponse} - AD user created or modified response
     * @author maleesahsa
     */
    CommonResponse createOrModifyAD(UserRequestDTO userRequest);
}
