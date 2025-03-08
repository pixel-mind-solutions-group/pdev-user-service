package com.pdev.user_service.service.impl.authorizeParty;

import com.pdev.user_service.controller.response.PageResponse;
import com.pdev.user_service.dto.authorizeParty.AuthorizePartyRequestDTO;
import com.pdev.user_service.dto.authorizeParty.AuthorizePartyResponseDTO;
import com.pdev.user_service.exception.RecordNotFoundException;
import com.pdev.user_service.mapper.authorizeParty.AuthorizePartyMapper;
import com.pdev.user_service.model.AuditData;
import com.pdev.user_service.model.authorizeParty.AuthorizeParty;
import com.pdev.user_service.repository.authorizeParty.AuthorizePartyRepository;
import com.pdev.user_service.service.authorizeParty.AuthorizePartyService;
import com.pdev.user_service.service.validation.authorizeParty.ValidateAuthorizeParty;
import com.pdev.user_service.util.CommonResponse;
import com.pdev.user_service.util.CommonUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service Implementation for managing {@link AuthorizeParty}.
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class AuthorizePartyServiceImpl implements AuthorizePartyService {

    private final AuthorizePartyMapper authorizePartyMapper;
    private final AuthorizePartyRepository authorizePartyRepository;
    private final CommonUtil commonUtil;
    private final ValidateAuthorizeParty validateAuthorizeParty;

    /**
     * This method is used to create or update authorize party
     *
     * @param authorizePartyRequest {@link AuthorizePartyRequestDTO} - authorize party request
     * @return {@link CommonResponse} - authorize party created or updated response
     * @author @maleeshasa
     */
    @Override
    @Transactional
    public CommonResponse createOrUpdate(AuthorizePartyRequestDTO authorizePartyRequest) {
        log.info("AuthorizePartyServiceImpl.createOrUpdate() => started.");
        AuthorizeParty authorizeParty = new AuthorizeParty();
        String message;

        // Validate authorize party request
        validateAuthorizeParty.validateAuthorizeParty(authorizePartyRequest);

        if (authorizePartyRequest.getId() != null) {
            message = "Authorize party is updated.";
            authorizeParty = authorizePartyRepository.findById(authorizePartyRequest.getId())
                    .orElseThrow(() -> new RecordNotFoundException("Authorize party is not exists."));
            authorizeParty.getAuditData().setUpdatedBy(commonUtil.getUsername());
            authorizeParty.getAuditData().setUpdatedOn(LocalDateTime.now());

        } else {
            message = "Authorize party is created.";
            authorizeParty.setAuditData(new AuditData(LocalDateTime.now(), commonUtil.getUsername()));
        }

        AuthorizeParty mappedEntity = authorizePartyMapper.mapToEntity(authorizeParty, authorizePartyRequest);
        try {
            return new CommonResponse(
                    HttpStatus.OK,
                    message,
                    authorizePartyMapper.mapToDTO(new AuthorizePartyResponseDTO(), authorizePartyRepository.save(mappedEntity))
            );
        } catch (Exception e) {
            log.error("Error while saving authorize party. Error: ", e);
            return new CommonResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Authorize party save failed.", null);
        }
    }

    /**
     * This method is used to get all authorize parties
     *
     * @return {@link CommonResponse} - all authorize parties
     * @author @maleeshasa
     */
    @Override
    public CommonResponse getAll() {
        List<AuthorizeParty> authorizeParties = authorizePartyRepository.findAll()
                .stream()
                .filter(a -> a.getActive().equals(Boolean.TRUE))
                .toList();
        if (!authorizeParties.isEmpty()) {
            return new CommonResponse(
                    HttpStatus.OK, "Authorize parties are exists.", authorizePartyMapper.mapToDTOList(authorizeParties)
            );
        } else {
            return new CommonResponse(
                    HttpStatus.NO_CONTENT, "Authorize party not found.", null
            );
        }
    }

    /**
     * This method is used to get all authorize parties with pagination
     *
     * @param of {@link PageRequest} - page request
     * @return {@link CommonResponse} - all authorize parties
     * @author @maleeshasa
     */
    @Override
    public CommonResponse getAllWithPage(PageRequest of) {
        Page<AuthorizeParty> authorizeParties = authorizePartyRepository.findAll(of);
        if (!authorizeParties.isEmpty()) {
            PageResponse pageResponse = PageResponse.builder()
                    .currentPage(authorizeParties.getNumber())
                    .totalPages(authorizeParties.getTotalPages())
                    .totalElements(authorizeParties.getTotalElements())
                    .dataList(authorizePartyMapper.mapToDTOList(authorizeParties.getContent())).build();

            return new CommonResponse(
                    HttpStatus.OK, "Authorize parties are exists.", pageResponse
            );
        } else {
            return new CommonResponse(
                    HttpStatus.NO_CONTENT, "Authorize party not found.", null
            );
        }
    }

    @Override
    public CommonResponse getById(int id) {
        AuthorizeParty authorizeParty = authorizePartyRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Authorize party is not exists."));
        return new CommonResponse(
                HttpStatus.OK, "Authorize party is exists.", authorizePartyMapper.mapToDTO(new AuthorizePartyResponseDTO(), authorizeParty)
        );
    }

    @Override
    public CommonResponse deleteById(int id) {
        AuthorizeParty party = authorizePartyRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Authorize party not found."));
        party.setActive(Boolean.FALSE);
        authorizePartyRepository.save(party);
        return new CommonResponse(
                HttpStatus.OK, "Authorize party is deleted.", null
        );
    }
}
