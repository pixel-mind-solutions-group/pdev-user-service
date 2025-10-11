package com.pdev.user_service.builder.email;

import com.pdev.user_service.constant.email.EmailBody;
import com.pdev.user_service.constant.email.EmailSubject;
import com.pdev.user_service.dto.event.email.EmailEventRequest;
import com.pdev.user_service.dto.user.UserRequestDTO;

import java.util.List;

public class EmailRequestBuilder {

    /**
     * This method is used to build email event request for user registered event
     *
     * @param userRequest {@link UserRequestDTO} - the user request
     * @return {@link EmailEventRequest} - the email event request
     * @author maleeshasa
     */
    public static EmailEventRequest getEmailEventRequest(UserRequestDTO userRequest) {
        EmailEventRequest eventRequest = new EmailEventRequest();
        eventRequest.setToEmails(List.of(userRequest.getEmail()));
        eventRequest.setSubject(EmailSubject.USER_REGISTERED);
        eventRequest.setBody(
                String.format(EmailBody.USER_REGISTERED_BODY,
                        userRequest.getFirstName().concat(" ").concat(userRequest.getLastName())
                )
        );
        return eventRequest;
    }
}
