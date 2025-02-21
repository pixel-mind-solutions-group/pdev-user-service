package com.pdev.user_service.service.validation;

import com.pdev.user_service.enums.CommonStatus;

import java.util.Arrays;

public class CommonValidation {

    public static boolean stringNullValidation(String inputString) {
        return inputString == null || inputString.isEmpty();
    }

    public static boolean integerNullValidation(Integer inputValue) {
        return inputValue == null;
    }

    public static boolean validStatus(String status) {
        return Arrays.stream(CommonStatus.values()).anyMatch(s -> s.getValue().equals(status));
    }
}
