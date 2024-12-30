package com.pdev.user_service.service;

import com.pdev.user_service.dto.user.userDetails.UserDetailsRequestDTO;
import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.util.CommonResponse;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
public interface UserService {

    /**
     * This method is allowed to create or modify user
     *
     * @param userRequest {@link UserRequestDTO} - user request details
     * @param userType    {@link String} - user type
     * @return {@link CommonResponse} - user created or modified response
     * @author maleesahsa
     */
    CommonResponse createOrModify(UserRequestDTO userRequest, String userType);

    /**
     * This method is allowed to get user by username
     *
     * @param userName {@link String} - user name
     * @return {@link CommonResponse} - user details response by username
     * @author maleesahsa
     */
    CommonResponse getByUserName(String userName);

    /**
     * This method is allowed to get user details uuid and access token
     *
     * @param userDetailsRequest {@link UserDetailsRequestDTO} - user details request
     * @return {@link CommonResponse} - user details response
     * @author maleesahsa
     */
    CommonResponse getByUserDetails(UserDetailsRequestDTO userDetailsRequest);
}
