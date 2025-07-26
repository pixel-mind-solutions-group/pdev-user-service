package com.pdev.user_service.service.validation.user;

import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.exception.BaseException;
import com.pdev.user_service.exception.RecordNotFoundException;
import com.pdev.user_service.exception.UnauthorizedException;
import com.pdev.user_service.model.applicationScope.ApplicationScope;
import com.pdev.user_service.model.user.external.pixelHR.PixelHRUser;
import com.pdev.user_service.model.user.internal.User;
import com.pdev.user_service.model.user.UserHasApplicationScopeHasUserRole;
import com.pdev.user_service.repository.user.UserHasApplicationScopeHasUserRoleRepository;
import com.pdev.user_service.repository.user.UserRepository;
import com.pdev.user_service.service.validation.CommonValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class ValidateUser {

    private final UserHasApplicationScopeHasUserRoleRepository userHasApplicationScopeHasUserRoleRepository;
    private final UserRepository userRepository;

    public void validateUser(User user) {
        log.info("ValidateUser.validateUser() => started.");
        if (user == null) {
            log.error("User is not exists.");
            throw new RecordNotFoundException("User is not exists.");

        } else if (user.getActive().equals(Boolean.FALSE)) {
            log.error("User is de-activated.");
            throw new UnauthorizedException("User is de-activated.");

        } else if (CommonValidation.stringNullValidation(user.getEmail())) {
            log.error("Email is not exists.");
            throw new BaseException(500, "User email is not exists.");

        } else if (user.getIsEmailVerified().equals(Boolean.FALSE)) {
            log.error("User's email is not verified.");
            throw new BaseException(500, "User's email is not verified.");

        } else if (user.getUserHasAuthorizeParties().isEmpty()) {
            log.error("Authorize party not found.");
            throw new RecordNotFoundException("User has no valid authorize party.");

        } else if (user.getUserHasAuthorizeParties().stream().allMatch(azp -> azp.getAuthorizeParty().getActive().equals(Boolean.FALSE))) {
            log.error("Authorize parties are in-active.");
            throw new RecordNotFoundException("Authorize parties are in-active.");

        } else if (user.getUserHasApplicationScopeHasUserRoles().isEmpty()) {
            log.error("User has no valid application scope.");
            throw new RecordNotFoundException("User has no valid application scope.");

        } else if (user.getFailCount() > 3) {
            log.error("Login attempts exceeded. Please contact help desk.");
            throw new UnauthorizedException("Login attempts exceeded. Please contact help desk.");

        } else if (user.getAccountNonLocked().equals(Boolean.FALSE)) {
            log.error("User account is locked. Please contact help desk.");
            throw new UnauthorizedException("User account is locked. Please contact help desk.");
        }
    }

    public void validateExternalUser(PixelHRUser user) {
        log.info("ValidateUser.validateExternalUser() => started.");
        if (user == null) {
            log.error("External User is not exists.");
            throw new RecordNotFoundException("User is not exists.");

        } else if (user.getActive().equals(Boolean.FALSE)) {
            log.error("External User is de-activated.");
            throw new UnauthorizedException("User is de-activated.");

        } else if (CommonValidation.stringNullValidation(user.getEmail())) {
            log.error("External User Email is not exists.");
            throw new BaseException(500, "User email is not exists.");

        } else if (user.getIsEmailVerified().equals(Boolean.FALSE)) {
            log.error("External User's email is not verified.");
            throw new BaseException(500, "User's email is not verified.");

        } else if (user.getUserHasAuthorizeParties().isEmpty()) {
            log.error("External User Authorize party not found.");
            throw new RecordNotFoundException("User has no valid authorize party.");

        } else if (user.getUserHasAuthorizeParties().stream().allMatch(azp -> azp.getAuthorizeParty().getActive().equals(Boolean.FALSE))) {
            log.error("External User Authorize parties are in-active.");
            throw new RecordNotFoundException("Authorize parties are in-active.");

        } else if (user.getUserHasApplicationScopeHasUserRoles().isEmpty()) {
            log.error("External User has no valid application scope.");
            throw new RecordNotFoundException("User has no valid application scope.");

        } else if (user.getFailCount() > 3) {
            log.error("External User Login attempts exceeded. Please contact help desk.");
            throw new UnauthorizedException("Login attempts exceeded. Please contact help desk.");

        } else if (user.getAccountNonLocked().equals(Boolean.FALSE)) {
            log.error("External User account is locked. Please contact help desk.");
            throw new UnauthorizedException("User account is locked. Please contact help desk.");
        }
    }

    public void validateUserApplicationScope(User user, ApplicationScope applicationScope) {
        log.info("ValidateUser.validateUserApplicationScope() => started.");
        List<UserHasApplicationScopeHasUserRole> applicationScopeHasUserRoles = userHasApplicationScopeHasUserRoleRepository.findByUserAndApplicationScopeAndActiveTrue(user, applicationScope);

        if (applicationScope == null) {
            log.error("UUID is invalid.");
            throw new RecordNotFoundException("UUID is invalid.");

        } else if (applicationScope.getActive().equals(Boolean.FALSE)) {
            log.error("Application scope is invalid.");
            throw new BaseException(500, "Application scope is invalid.");

        } else if (applicationScopeHasUserRoles.isEmpty()) {
            throw new RecordNotFoundException("User is unauthorized for the provided uuid related application.");

        } else if (applicationScopeHasUserRoles.size() > 1) {
            throw new BaseException(422, "User has duplicate application scopes.");
        }
    }

    public void validateExternalUserApplicationScope(PixelHRUser user, ApplicationScope applicationScope) {
        log.info("ValidateUser.validateUserApplicationScope() => started.");
        List<UserHasApplicationScopeHasUserRole> applicationScopeHasUserRoles = userHasApplicationScopeHasUserRoleRepository.findByPixelHRUserAndApplicationScopeAndActiveTrue(user, applicationScope);

        if (applicationScope == null) {
            log.error("UUID is invalid.");
            throw new RecordNotFoundException("UUID is invalid.");

        } else if (applicationScope.getActive().equals(Boolean.FALSE)) {
            log.error("Application scope is invalid.");
            throw new BaseException(500, "Application scope is invalid.");

        } else if (applicationScopeHasUserRoles.isEmpty()) {
            throw new RecordNotFoundException("User is unauthorized for the provided uuid related application.");

        } else if (applicationScopeHasUserRoles.size() > 1) {
            throw new BaseException(422, "User has duplicate application scopes.");
        }
    }

    public void validateNonAdUserCreate(UserRequestDTO userRequest) {
        User userByUname = userRepository.findByUserName(userRequest.getUserName());
        if (userByUname != null) {
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "User name already in used.");
        }
        User userByEmail = userRepository.findByEmail(userRequest.getEmail());
        if (userByEmail != null) {
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Email is already in used.");
        }
    }
}
