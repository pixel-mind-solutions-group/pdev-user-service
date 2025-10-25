package com.pdev.user_service.builder.email;

import com.pdev.user_service.constant.email.EmailBody;
import com.pdev.user_service.constant.email.EmailSubject;
import com.pdev.user_service.dto.event.email.EmailEventRequest;
import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.model.user.internal.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.pdev.user_service.constant.CommonConstants.PDEV_USER;

@Component
public class EmailRequestBuilder {

    @Value("${email.verification.link}")
    private String emailVerificationLink;

    /**
     * This method is used to build email event request for user registered event
     *
     * @param userRequest {@link UserRequestDTO} - the user request
     * @param savedUser
     * @return {@link EmailEventRequest} - the email event request
     * @author maleeshasa
     */
    public EmailEventRequest getUserRegistrationEmailEventRequest(UserRequestDTO userRequest, User savedUser) {
        EmailEventRequest eventRequest = new EmailEventRequest();
        eventRequest.setApplicationSource(PDEV_USER);
        eventRequest.setToEmails(List.of(userRequest.getEmail()));
        eventRequest.setSubject(EmailSubject.USER_REGISTERED);
        eventRequest.setBody(
                String.format(
                        EmailBody.USER_REGISTERED_BODY,
                        userRequest.getUserName(),
                        emailVerificationLink + savedUser.getId(),
                        emailVerificationLink + savedUser.getId()
                )
        );
        eventRequest.setIsHtml(Boolean.TRUE);
        return eventRequest;
    }

    /**
     * This method is allowed to construct email request to publish email verification url
     *
     * @param pixelHireCandidate {@link User} - user who is receiving verification url
     * @return {@link EmailEventRequest} - email event request
     * @author @maleeshasa
     */
    public EmailEventRequest getEmailVerificationURLEventRequest(User pixelHireCandidate) {
        EmailEventRequest eventRequest = new EmailEventRequest();
        eventRequest.setApplicationSource(PDEV_USER);
        eventRequest.setToEmails(List.of(pixelHireCandidate.getEmail()));
        eventRequest.setSubject(EmailSubject.USER_REGISTERED);
        eventRequest.setBody(
                String.format(
                        EmailBody.USER_REGISTERED_BODY,
                        pixelHireCandidate.getUserName(),
                        emailVerificationLink + pixelHireCandidate.getId(),
                        emailVerificationLink + pixelHireCandidate.getId()
                )
        );
        eventRequest.setIsHtml(Boolean.TRUE);
        return eventRequest;
    }
}
