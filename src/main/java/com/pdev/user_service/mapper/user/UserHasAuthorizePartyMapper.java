package com.pdev.user_service.mapper.user;

import com.pdev.user_service.dto.user.UserRequestDTO;
import com.pdev.user_service.exception.RecordNotFoundException;
import com.pdev.user_service.model.AuditData;
import com.pdev.user_service.model.user.internal.User;
import com.pdev.user_service.model.user.UserHasAuthorizeParty;
import com.pdev.user_service.repository.authorizeParty.AuthorizePartyRepository;
import com.pdev.user_service.util.CommonUtil;
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

    private final CommonUtil commonUtil;
    private final AuthorizePartyRepository authorizePartyRepository;

    public UserHasAuthorizeParty mapToADEntity(UserHasAuthorizeParty hasAuthorizeParty, User user, Integer authorizePartyId) {
        log.info("UserHasAuthorizePartyMapper.mapToEntity() => started.");
        hasAuthorizeParty.setAuthorizeParty(authorizePartyRepository.findById(authorizePartyId)
                .orElseThrow(() -> new RecordNotFoundException("Authorize party is not exists.")));
        hasAuthorizeParty.setUser(user);
        hasAuthorizeParty.setAuditData(new AuditData(LocalDateTime.now(), commonUtil.getUsername()));
        hasAuthorizeParty.setActive(Boolean.TRUE);
        log.info("UserHasAuthorizePartyMapper.mapToEntity() => ended.");
        return hasAuthorizeParty;
    }

    public UserHasAuthorizeParty mapToNonADEntity(UserHasAuthorizeParty hasAuthorizeParty, User user, String party) {
        log.info("UserHasAuthorizePartyMapper.mapToNonADEntity() => started.");
        hasAuthorizeParty.setAuthorizeParty(authorizePartyRepository.findByParty(party)
                .orElseThrow(() -> new RecordNotFoundException("Authorize party is not exists.")));
        hasAuthorizeParty.setUser(user);
        hasAuthorizeParty.setAuditData(new AuditData(LocalDateTime.now(), commonUtil.getUsername()));
        hasAuthorizeParty.setActive(Boolean.TRUE);
        log.info("UserHasAuthorizePartyMapper.mapToNonADEntity() => ended.");
        return hasAuthorizeParty;
    }

    public List<UserHasAuthorizeParty> mapToADEntities(UserRequestDTO userRequest, User user) {
        log.info("UserHasAuthorizePartyMapper.mapToADEntities() => started.");
        List<UserHasAuthorizeParty> parties = new ArrayList<>();
        userRequest.getUserHasAuthorizePartyIds().forEach(id -> parties.add(mapToADEntity(new UserHasAuthorizeParty(), user, id)));
        log.info("UserHasAuthorizePartyMapper.mapToADEntities() => ended.");
        return parties;
    }

    public List<UserHasAuthorizeParty> mapToNonADEntities(UserRequestDTO userRequest, User user) {
        log.info("UserHasAuthorizePartyMapper.mapToNonADEntities() => started.");
        List<UserHasAuthorizeParty> parties = new ArrayList<>();
        userRequest.getUserHasAuthorizeParties().forEach(party -> parties.add(mapToNonADEntity(new UserHasAuthorizeParty(), user, party)));
        log.info("UserHasAuthorizePartyMapper.mapToNonADEntities() => ended.");
        return parties;
    }
}
