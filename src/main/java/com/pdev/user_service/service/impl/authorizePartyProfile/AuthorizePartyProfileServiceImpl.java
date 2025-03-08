package com.pdev.user_service.service.impl.authorizePartyProfile;

import com.pdev.user_service.controller.response.PageResponse;
import com.pdev.user_service.dto.authorizeParty.AuthorizePartyResponseDTO;
import com.pdev.user_service.dto.authorizePartyProfile.AuthorizePartyProfileRequestDTO;
import com.pdev.user_service.dto.authorizePartyProfile.AuthorizePartyProfileResponseDTO;
import com.pdev.user_service.exception.RecordNotFoundException;
import com.pdev.user_service.mapper.authorizeParty.AuthorizePartyMapper;
import com.pdev.user_service.mapper.authorizePartyProfile.AuthorizePartyProfileMapper;
import com.pdev.user_service.mapper.authorizePartyRole.AuthorizePartyRoleMapper;
import com.pdev.user_service.model.authorizeParty.AuthorizeParty;
import com.pdev.user_service.model.authorizeParty.AuthorizePartyHasAuthorizePartyRole;
import com.pdev.user_service.repository.authorizeParty.AuthorizePartyHasAuthorizePartyRoleRepository;
import com.pdev.user_service.repository.authorizeParty.AuthorizePartyRepository;
import com.pdev.user_service.service.authorizePartyProfile.AuthorizePartyProfileService;
import com.pdev.user_service.service.validation.CommonValidation;
import com.pdev.user_service.service.validation.authorizePartyProfile.ValidateAuthorizePartyProfile;
import com.pdev.user_service.util.CommonResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthorizePartyProfileServiceImpl implements AuthorizePartyProfileService {

    private final ValidateAuthorizePartyProfile validateAuthorizePartyProfile;
    private final AuthorizePartyHasAuthorizePartyRoleRepository authorizePartyHasAuthorizePartyRoleRepository;
    private final AuthorizePartyRepository authorizePartyRepository;
    private final AuthorizePartyProfileMapper authorizePartyProfileMapper;
    private final AuthorizePartyMapper authorizePartyMapper;
    private final AuthorizePartyRoleMapper authorizePartyRoleMapper;

    @Override
    @Transactional
    public CommonResponse createOrUpdate(AuthorizePartyProfileRequestDTO authorizePartyProfileRequest) {
        log.info("AuthorizePartyProfileServiceImpl.createOrUpdate() => started.");
        String message;

        // Validate authorize party profile request
        validateAuthorizePartyProfile.validateAuthorizePartyProfile(authorizePartyProfileRequest);

        AuthorizeParty authorizeParty = authorizePartyRepository.findById(authorizePartyProfileRequest.getAuthorizeParty())
                .orElseThrow(() -> new RecordNotFoundException("Authorize party is not exists."));

        try {
            authorizePartyHasAuthorizePartyRoleRepository.deleteAllByAuthorizeParty(authorizeParty);
            log.info("Existing authorize party profiles are deleted.");

        } catch (Exception e) {
            log.error("Error while deleting authorize party profiles. Error: ", e);
            return new CommonResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Authorize party profiles delete failed for auth party.", null);
        }

        try {
            return new CommonResponse(
                    HttpStatus.OK,
                    "Authorize party profile is created.",
                    authorizePartyProfileMapper.mapToDTOList(
                            authorizePartyHasAuthorizePartyRoleRepository.saveAll(
                                    authorizePartyProfileMapper.mapToEntities(authorizePartyProfileRequest)))
            );
        } catch (Exception e) {
            log.error("Error while saving authorize party profile. Error: ", e);
            return new CommonResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Authorize party profile save failed.", null);
        }
    }

    @Override
    public CommonResponse getAll() {
        List<AuthorizeParty> authorizeParties = authorizePartyRepository.findAll();
        List<AuthorizePartyProfileResponseDTO> list = authorizeParties.stream()
                .map(authorizeParty -> {

                    List<AuthorizePartyHasAuthorizePartyRole> hasAuthorizePartyRoles =
                            authorizePartyHasAuthorizePartyRoleRepository.findByAuthorizeParty(authorizeParty);

                    AuthorizePartyProfileResponseDTO dto = new AuthorizePartyProfileResponseDTO();
                    dto.setAuthorizeParty(authorizePartyMapper.mapToDTO(new AuthorizePartyResponseDTO(), authorizeParty));
                    return dto;
                }).toList();

        if (!authorizeParties.isEmpty()) {
            return new CommonResponse(HttpStatus.OK, "Authorize profiles are exists.", list);
        } else {
            return new CommonResponse(HttpStatus.NO_CONTENT, "Authorize profiles are not exists.", null);
        }
    }

    @Override
    public CommonResponse getAllWithPage(PageRequest of) {
        Page<AuthorizeParty> authorizeParties = authorizePartyRepository.findAll(of);
        List<AuthorizePartyProfileResponseDTO> list = authorizeParties.stream()
                .map(authorizeParty -> {

                    List<AuthorizePartyHasAuthorizePartyRole> hasAuthorizePartyRoles =
                            authorizePartyHasAuthorizePartyRoleRepository.findByAuthorizeParty(authorizeParty);

                    AuthorizePartyProfileResponseDTO dto = new AuthorizePartyProfileResponseDTO();
                    dto.setAuthorizePartyId(authorizeParty.getId());
                    dto.setAuthorizeParty(authorizePartyMapper.mapToDTO(new AuthorizePartyResponseDTO(), authorizeParty));

                    StringBuilder builder = new StringBuilder();
                    for (AuthorizePartyHasAuthorizePartyRole r : hasAuthorizePartyRoles) {
                        builder.append(r.getAuthorizePartyRole().getRole()).append(", ");
                    }
                    String permissions = builder.toString();
                    if (!CommonValidation.stringNullValidation(permissions)) {
                        permissions = permissions.substring(0, permissions.length() - 2);
                        dto.setAuthorizePartyRoles(permissions);
                    }
                    return dto;
                }).toList();

        PageResponse pageResponse = PageResponse.builder()
                .currentPage(authorizeParties.getNumber())
                .totalPages(authorizeParties.getTotalPages())
                .totalElements(authorizeParties.getTotalElements())
                .dataList(list).build();

        if (!authorizeParties.isEmpty()) {
            return new CommonResponse(HttpStatus.OK, "Authorize profiles are exists.", pageResponse);
        } else {
            return new CommonResponse(HttpStatus.NO_CONTENT, "Authorize profiles are not exists.", null);
        }
    }

    @Override
    public CommonResponse getAllByAuthParty(Integer id) {

        AuthorizeParty authorizeParty = authorizePartyRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Auth party not found."));

        List<AuthorizePartyHasAuthorizePartyRole> hasAuthorizePartyRoles =
                authorizePartyHasAuthorizePartyRoleRepository.findByAuthorizeParty(authorizeParty);

        List<String> list = hasAuthorizePartyRoles.stream()
                .map(r -> r.getAuthorizePartyRole().getId().toString())
                .toList();

        if (!list.isEmpty()) {
            return new CommonResponse(HttpStatus.OK, "Authorize profiles are exists.", list);
        } else {
            return new CommonResponse(HttpStatus.NO_CONTENT, "Authorize profiles are not exists.", null);
        }
    }

    @Override
    public CommonResponse deleteByAuthParty(int id) {

        AuthorizeParty authorizeParty = authorizePartyRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Auth party not found."));

        authorizePartyHasAuthorizePartyRoleRepository.deleteAll(authorizeParty.getAuthorizePartyHasPartyRoles());
        return new CommonResponse(
                HttpStatus.OK, "Authorize party roles are deleted.", null
        );
    }
}
