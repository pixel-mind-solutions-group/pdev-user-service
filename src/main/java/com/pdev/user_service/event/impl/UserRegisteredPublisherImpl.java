package com.pdev.user_service.event.impl;

import com.pdev.user_service.builder.email.EmailRequestBuilder;
import com.pdev.user_service.constant.EventLogicalBindName;
import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.event.UserRegisteredPublisher;
import com.pdev.user_service.model.user.internal.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * This class is used to publish user registration related events
 *
 * @author maleeshasa
 * @Date 2024/11/16
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class UserRegisteredPublisherImpl implements UserRegisteredPublisher {

    private final StreamBridge streamBridge;
    private final EmailRequestBuilder emailRequestBuilder;

    /**
     * This method is used to publish user registered event
     *
     * @author maleeshasa
     */
    @Async("userRegistrationVTExecutor")
    @Override
    public void publishUserRegisteredEvent(UserRequestDTO userRequest, User savedUser) {
        log.info("Publishing user registered event asynchronously using virtual thread");

        // Here you would typically send the event to a message broker like Kafka or RabbitMQ
        streamBridge.send(
                EventLogicalBindName.USER_REGISTRATION_EMAIL_OUT_0,
                emailRequestBuilder.getUserRegistrationEmailEventRequest(userRequest, savedUser)
        );
    }

    /**
     * This method is used to publish email verification url event
     *
     * @author maleeshasa
     */
    @Async("emailVerificationURLVTExecutor")
    @Override
    public void publishEmailVerificationURLEvent(User pixelHireCandidateUser) {
        log.info("Publishing email verification url event asynchronously using virtual thread");

        // Here you would typically send the event to a message broker like Kafka or RabbitMQ
        streamBridge.send(
                EventLogicalBindName.EMAIL_VERIFICATION_URL_OUT_0,
                emailRequestBuilder.getEmailVerificationURLEventRequest(pixelHireCandidateUser)
        );
    }
}
