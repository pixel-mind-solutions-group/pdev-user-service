package com.pdev.user_service.mapper.authorizePartyProfile;

import com.pdev.user_service.dto.authorizeParty.AuthorizePartyResponseDTO;
import com.pdev.user_service.dto.authorizePartyProfile.AuthorizePartyProfileRequestDTO;
import com.pdev.user_service.dto.authorizePartyProfile.AuthorizePartyProfileResponseDTO;
import com.pdev.user_service.dto.authorizePartyRole.AuthorizePartyRoleResponseDTO;
import com.pdev.user_service.exception.RecordNotFoundException;
import com.pdev.user_service.mapper.authorizeParty.AuthorizePartyMapper;
import com.pdev.user_service.mapper.authorizePartyRole.AuthorizePartyRoleMapper;
import com.pdev.user_service.model.AuditData;
import com.pdev.user_service.model.authorizeParty.AuthorizeParty;
import com.pdev.user_service.model.authorizeParty.AuthorizePartyHasAuthorizePartyRole;
import com.pdev.user_service.model.authorizePartyRole.AuthorizePartyRole;
import com.pdev.user_service.repository.authorizeParty.AuthorizePartyRepository;
import com.pdev.user_service.repository.authorizePartyRole.AuthorizePartyRoleRepository;
import com.pdev.user_service.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class AuthorizePartyProfileMapper {

    private final AuthorizePartyMapper authorizePartyMapper;
    private final AuthorizePartyRoleMapper authorizePartyRoleMapper;
    private final AuthorizePartyRepository authorizePartyRepository;
    private final AuthorizePartyRoleRepository authorizePartyRoleRepository;
    private final CommonUtil commonUtil;

    public List<AuthorizePartyHasAuthorizePartyRole> mapToEntities(AuthorizePartyProfileRequestDTO authorizePartyProfileRequest) {
        List<AuthorizePartyHasAuthorizePartyRole> authorizePartyHasAuthorizePartyRoles = new ArrayList<>();
        AuthorizeParty authorizeParty = authorizePartyRepository.findById(authorizePartyProfileRequest.getAuthorizeParty())
                .orElseThrow(() -> new RecordNotFoundException("Authorize party is not exists."));

        authorizePartyProfileRequest.getAuthorizePartyRoles().forEach(role -> {
            AuthorizePartyRole authorizePartyRole = authorizePartyRoleRepository.findById(role)
                    .orElseThrow(() -> new RecordNotFoundException("Authorize party role is not exists."));
            AuthorizePartyHasAuthorizePartyRole entity = new AuthorizePartyHasAuthorizePartyRole();
            entity.setAuthorizeParty(authorizeParty);
            entity.setAuthorizePartyRole(authorizePartyRole);
            entity.setAuditData(new AuditData(LocalDateTime.now(), commonUtil.getUsername()));
            authorizePartyHasAuthorizePartyRoles.add(entity);
        });

        return authorizePartyHasAuthorizePartyRoles;
    }

    public List<AuthorizePartyProfileResponseDTO> mapToDTOList(List<AuthorizePartyHasAuthorizePartyRole> entities) {
        return entities.stream()
                .map(entity -> {
                    AuthorizePartyProfileResponseDTO dto = new AuthorizePartyProfileResponseDTO();
                    dto.setId(entity.getId());
                    dto.setAuthorizeParty(authorizePartyMapper.mapToDTO(new AuthorizePartyResponseDTO(), entity.getAuthorizeParty()));
                    dto.setAuthorizePartyRole(authorizePartyRoleMapper.mapToDTO(new AuthorizePartyRoleResponseDTO(), entity.getAuthorizePartyRole()));
                    return dto;
                }).toList();
    }
}
