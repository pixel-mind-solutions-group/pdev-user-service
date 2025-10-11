package com.pdev.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * This class is used to configure thread pools for asynchronous processing
 *
 * @author maleeshasa
 * @Date 2024/11/16
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    /**
     * This bean is used to create a virtual thread executor for user registration related tasks
     *
     * @return Executor - the virtual thread executor
     * @author maleeshasa
     */
    @Bean(name = "userRegistrationVTExecutor")
    public Executor userRegistrationVTExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}
