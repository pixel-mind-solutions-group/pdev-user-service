package com.pdev.user_service.mapper.authorizeParty;

import com.pdev.user_service.dto.authorizeParty.AuthorizePartyResponseDTO;
import com.pdev.user_service.mapper.authorizePartyRole.AuthorizePartyRoleMapper;
import com.pdev.user_service.model.authorizeParty.AuthorizeParty;
import com.pdev.user_service.model.authorizeParty.AuthorizePartyHasAuthorizePartyRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizePartyMapper {

    private final AuthorizePartyRoleMapper authorizePartyRoleMapper;

    public AuthorizePartyResponseDTO mapToDTO(AuthorizePartyResponseDTO dto, AuthorizeParty authorizeParty) {
        log.info("AuthorizePartyMapper.mapToDTO() => started.");
        dto.setAuthorizePartyId(authorizeParty.getId());
        dto.setParty(authorizeParty.getParty());
        dto.setActive(authorizeParty.getActive());
        dto.setAuthorizePartyRoles(
                authorizePartyRoleMapper.mapToList(
                        authorizeParty.getAuthorizePartyHasPartyRoles().stream()
                                .map(AuthorizePartyHasAuthorizePartyRole::getAuthorizePartyRole)
                                .toList()
                ));
        log.info("AuthorizePartyMapper.mapToDTO() => ended.");
        return dto;
    }

    public List<AuthorizePartyResponseDTO> mapToList(List<AuthorizeParty> authorizeParties) {
        log.info("AuthorizePartyMapper.mapToList() => started.");
        List<AuthorizePartyResponseDTO> dtoList = new ArrayList<>();
        if (!authorizeParties.isEmpty()) {
            dtoList = authorizeParties.stream()
                    .map(authorizeParty -> mapToDTO(new AuthorizePartyResponseDTO(), authorizeParty))
                    .toList();
        }
        log.info("AuthorizePartyMapper.mapToList() => ended.");
        return dtoList;
    }
}
