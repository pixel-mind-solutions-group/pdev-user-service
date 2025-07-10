package com.pdev.user_service.enums;

public enum MFAStatus {
    INITIATED("Initiated"),
    PASSWORD_RESET("Password_reset"),
    QR_SCAN("qr_scan"),
    AUTH_CODE("auth_code"),
    REGISTERED("Registered");

    private final String value;

    MFAStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
