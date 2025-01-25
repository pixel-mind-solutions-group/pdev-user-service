package com.pdev.user_service.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RecordNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -6638849627494498004L;

    public RecordNotFoundException(String message) {
        super(message);
    }
}

