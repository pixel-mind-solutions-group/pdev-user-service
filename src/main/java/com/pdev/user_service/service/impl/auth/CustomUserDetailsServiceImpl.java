package com.pdev.user_service.service.impl.auth;

import com.pdev.user_service.model.user.User;
import com.pdev.user_service.repository.user.UserRepository;
import com.pdev.user_service.service.auth.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Slf4j
@Service
@AllArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("CustomUserDetailsServiceImpl.loadUserByUsername() => started.");
        User user = userRepository.findByUserName(username);
        if (Objects.isNull(user)) {
            log.error("User not available.");
            throw new UsernameNotFoundException("User not available.");
        }
        log.info("CustomUserDetailsServiceImpl.loadUserByUsername() => ended.");
        return new CustomerUserDetailsImpl(user);
    }

}
