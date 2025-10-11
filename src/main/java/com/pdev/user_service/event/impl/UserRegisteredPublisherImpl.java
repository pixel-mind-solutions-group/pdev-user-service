package com.pdev.user_service.event.impl;

import com.pdev.user_service.constant.EventLogicalBindName;
import com.pdev.user_service.dto.event.email.EmailEventRequest;
import com.pdev.user_service.event.UserRegisteredPublisher;
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

    /**
     * This method is used to publish user registered event
     *
     * @param emailEventRequest {@link EmailEventRequest} - the email event request
     * @author maleeshasa
     */
    @Async("userRegistrationVTExecutor")
    @Override
    public void publishUserRegisteredEvent(EmailEventRequest emailEventRequest) {
        log.info("Publishing user registered event asynchronously using virtual thread: {}", emailEventRequest);
        // Here you would typically send the event to a message broker like Kafka or RabbitMQ
        streamBridge.send(EventLogicalBindName.USER_REGISTRATION_OUT_0, emailEventRequest);
    }
}
