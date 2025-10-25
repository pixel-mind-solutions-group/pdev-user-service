package com.pdev.user_service.dto.event.sms;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SmsEventRequest {
    private List<String> mobileNumbers = new ArrayList<>();
    private String sms;
    private String serviceProvider;
    private Integer priority;
    private LocalDateTime scheduleDateTime;
}
