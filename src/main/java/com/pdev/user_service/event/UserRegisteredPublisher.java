package com.pdev.user_service.event;

import com.pdev.user_service.dto.event.email.EmailEventRequest;

public interface UserRegisteredPublisher {

    /**
     * This method is used to publish user registered event
     *
     * @param emailEventRequest {@link EmailEventRequest} - the email event request
     * @author maleeshasa
     */
    void publishUserRegisteredEvent(EmailEventRequest emailEventRequest);
}
