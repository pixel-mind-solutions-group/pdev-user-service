package com.pdev.user_service.builder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@Slf4j
@RequiredArgsConstructor
public class OneTimePasswordBuilder {

    @Value("${one.time.password.CHAR_POOL}")
    private String CHAR_POOL;

    @Value("${one.time.password.PASSWORD_LENGTH}")
    private Integer PASSWORD_LENGTH;

    private static SecureRandom RANDOM = new SecureRandom();

    public String generatePassword() {
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = RANDOM.nextInt(CHAR_POOL.length());
            password.append(CHAR_POOL.charAt(index));
        }
        log.info("One time password is generated: {}", password);
        return password.toString();
    }
}
