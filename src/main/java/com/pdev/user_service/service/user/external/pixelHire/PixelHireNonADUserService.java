package com.pdev.user_service.service.user.external.pixelHire;

import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.util.CommonResponse;

/**
 * @author maleeshasa
 * @Date 2024/11/15
 */
public interface PixelHireNonADUserService {

    /**
     * This method is allowed to create or modify Non AD user
     *
     * @param userRequest {@link UserRequestDTO} - Non AD user request details
     * @return {@link CommonResponse} - Non AD user created or modified response
     * @author maleesahsa
     */
    CommonResponse createNonAD(UserRequestDTO userRequest);

    /**
     * This method is allowed to verify email of pixel hire non AD user
     *
     * @param userId {@link Integer} - user id
     * @param verify {@link Boolean} - verify or unverify email
     * @return {@link CommonResponse} - email verified or unverified response
     * @author @maleesahsa
     */
    CommonResponse verifyEmail(Integer userId, Boolean verify);

    /**
     * This method is allowed to send verification email to pixel hire non AD user
     *
     * @param username {@link String} - username of the user
     * @return {@link CommonResponse} - email sent response
     * @author @maleesahsa
     */
    CommonResponse sendVerificationEmail(String username);
}
