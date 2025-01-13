package com.pdev.user_service.enums;

import lombok.Getter;

@Getter
public enum UserType {

    AD("active_directory_user"),
    NON_AD("non_active_directory_user");

    private final String name;

    UserType(String name) {
        this.name = name;
    }
}
