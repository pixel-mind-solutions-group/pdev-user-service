package com.pdev.user_service.enums;

import lombok.Getter;

@Getter
public enum UserType {

    AD("Active Directory"),
    NON_AD("Non Active Directory");

    private final String name;

    UserType(String name) {
        this.name = name;
    }
}
