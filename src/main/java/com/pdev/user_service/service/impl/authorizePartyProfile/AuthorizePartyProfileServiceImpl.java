package com.pdev.user_service.service.impl.authorizePartyProfile;

import com.pdev.user_service.dto.authorizePartyProfile.AuthorizePartyProfileRequestDTO;
import com.pdev.user_service.exception.RecordNotFoundException;
import com.pdev.user_service.mapper.authorizePartyProfile.AuthorizePartyProfileMapper;
import com.pdev.user_service.model.authorizeParty.AuthorizeParty;
import com.pdev.user_service.repository.authorizeParty.AuthorizePartyHasAuthorizePartyRoleRepository;
import com.pdev.user_service.repository.authorizeParty.AuthorizePartyRepository;
import com.pdev.user_service.service.authorizePartyProfile.AuthorizePartyProfileService;
import com.pdev.user_service.service.validation.authorizePartyProfile.ValidateAuthorizePartyProfile;
import com.pdev.user_service.util.CommonResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthorizePartyProfileServiceImpl implements AuthorizePartyProfileService {

    private final ValidateAuthorizePartyProfile validateAuthorizePartyProfile;
    private final AuthorizePartyHasAuthorizePartyRoleRepository authorizePartyHasAuthorizePartyRoleRepository;
    private final AuthorizePartyRepository authorizePartyRepository;
    private final AuthorizePartyProfileMapper authorizePartyProfileMapper;

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
        return null;
    }

    @Override
    public CommonResponse getAllWithPage(PageRequest of) {
        return null;
    }
}
