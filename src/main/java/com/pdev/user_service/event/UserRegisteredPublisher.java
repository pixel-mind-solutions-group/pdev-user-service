package com.pdev.user_service.event;

import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.model.user.internal.User;

public interface UserRegisteredPublisher {

    /**
     * This method is used to publish user registered event
     *
     * @author maleeshasa
     */
    void publishUserRegisteredEvent(UserRequestDTO userRequest, User savedUser);

    void publishEmailVerificationURLEvent(User pixelHireCandidateUser);
}
