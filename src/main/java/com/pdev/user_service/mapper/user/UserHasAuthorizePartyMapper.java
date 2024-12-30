package com.pdev.user_service.mapper.user;

import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.exception.RecordNotFoundException;
import com.pdev.user_service.model.AuditData;
import com.pdev.user_service.model.user.User;
import com.pdev.user_service.model.user.UserHasAuthorizeParty;
import com.pdev.user_service.repository.authorizeParty.AuthorizePartyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserHasAuthorizePartyMapper {

    private final AuthorizePartyRepository authorizePartyRepository;

    public UserHasAuthorizeParty mapToEntity(UserHasAuthorizeParty hasAuthorizeParty, User user, Integer authorizePartyId) {
        log.info("UserHasAuthorizePartyMapper.mapToEntity() => started.");
        hasAuthorizeParty.setAuthorizeParty(authorizePartyRepository.findById(authorizePartyId)
                .orElseThrow(() -> new RecordNotFoundException("Authorize party is not exists.")));
        hasAuthorizeParty.setUser(user);
        hasAuthorizeParty.setAuditData(new AuditData(LocalDateTime.now(), "admin"));
        hasAuthorizeParty.setActive(Boolean.TRUE);
        log.info("UserHasAuthorizePartyMapper.mapToEntity() => ended.");
        return hasAuthorizeParty;
    }

    public List<UserHasAuthorizeParty> mapToEntities(UserRequestDTO userRequest, User user) {
        log.info("UserHasAuthorizePartyMapper.mapToEntities() => started.");
        List<UserHasAuthorizeParty> parties = new ArrayList<>();
        userRequest.getUserHasAuthorizePartyIds().forEach(id -> parties.add(mapToEntity(new UserHasAuthorizeParty(), user, id)));
        log.info("UserHasAuthorizePartyMapper.mapToEntities() => ended.");
        return parties;
    }
}
