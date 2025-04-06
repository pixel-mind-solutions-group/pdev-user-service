package com.pdev.user_service.config;

import com.pdev.user_service.constant.RolePermissionsConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Enable @PreAuthorize and @PostAuthorize
public class WebSecurityConfig {

    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Configures the Spring Security filter chain for the application. This method defines
     * security policies such as endpoint access rules, session management settings,
     * and the inclusion of custom filters.
     *
     * @param httpSecurity {@link HttpSecurity} - the security configuration object provided by Spring Security
     * @return {@link SecurityFilterChain} - the configured security filter chain, which Spring uses to process incoming requests
     * @configuration: 1. Disables CSRF protection for stateless applications.
     * 2. Defines endpoint-specific access rules:
     * - Public access to health check and authentication endpoints.
     * - Role-based access to user-related endpoints requiring the "PERMISSION_USER_AUTH_SERVICE" authority.
     * - Authentication required for all other endpoints.
     * 3. Sets session management to `STATELESS` mode, as JWTs are used for stateless authentication.
     * 4. Adds a custom JWT authentication filter (`jwtAuthenticationFilter`) before the default
     * `UsernamePasswordAuthenticationFilter`.
     * @author maleeshasa
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        log.info("WebSecurityConfig.securityFilterChain() => started.");
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults()) // by default use a bean by the name of corsConfigurationSource
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/health/healthChecker").permitAll()
                                .requestMatchers("/api/iam/v1/auth/user/token").permitAll()
                                .requestMatchers("/api/iam/v1/user/get-by-username/**").permitAll()
                                .requestMatchers("/api/iam/v1/user/non-ad/create").permitAll()

                                // TODO: should remove after configure authorization for below endpoints
                                .requestMatchers("/api/iam/application-scope/v1/**").permitAll()
                                .requestMatchers("/api/iam/component-element/v1/**").permitAll()
                                .requestMatchers("/api/iam/component/v1/**").permitAll()
                                .requestMatchers("/api/iam/module/v1/**").permitAll()
                                .requestMatchers("/api/iam/authorize-party/v1/**").permitAll()
                                .requestMatchers("/api/iam/authorize-party-role/v1/**").permitAll()
                                .requestMatchers("/api/iam/authorize-party-profile/v1/**").permitAll()
                                .requestMatchers("/api/iam/user-role/v1/**").permitAll()
                                .requestMatchers("/api/iam/access-control/v1/**").permitAll()

                                .requestMatchers("/api/iam/v1/user/**").hasAuthority(RolePermissionsConstants.PERMISSION_USER_AUTH_SERVICE)
                                .anyRequest().authenticated()
                ).sessionManagement(ses -> ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        log.info("WebSecurityConfig.securityFilterChain() => ended.");
        return httpSecurity
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(List.of("Authorization", "*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        log.info("WebSecurityConfig.bCryptPasswordEncoder() => started.");
        return new BCryptPasswordEncoder(14);
    }
}
