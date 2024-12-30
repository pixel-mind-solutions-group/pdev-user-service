package com.pdev.user_service.service.impl;

import com.pdev.user_service.model.user.User;
import com.pdev.user_service.service.CustomerUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Service
public class CustomerUserDetailsImpl implements CustomerUserDetails {

    private User user;

    public CustomerUserDetailsImpl() {
    }

    public CustomerUserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Map<String, SimpleGrantedAuthority> authorityMap = new HashMap<>();
        user.getUserHasApplicationScopeHasUserRoles()
                .forEach(ur -> {
                    authorityMap.put(ur.getApplicationScope().getScope(), new SimpleGrantedAuthority(ur.getUserRole().getRole()));
                });
        return authorityMap.values();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
