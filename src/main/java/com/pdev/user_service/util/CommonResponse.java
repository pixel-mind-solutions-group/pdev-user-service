package com.pdev.user_service.util;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse {
    private HttpStatus status;
    private String message;
    private Object data;
}
