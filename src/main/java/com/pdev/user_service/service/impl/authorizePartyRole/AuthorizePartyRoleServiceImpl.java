package com.pdev.user_service.service.impl.authorizePartyRole;

import com.pdev.user_service.controller.response.PageResponse;
import com.pdev.user_service.dto.authorizePartyRole.AuthorizePartyRoleRequestDTO;
import com.pdev.user_service.dto.authorizePartyRole.AuthorizePartyRoleResponseDTO;
import com.pdev.user_service.exception.RecordNotFoundException;
import com.pdev.user_service.mapper.authorizePartyRole.AuthorizePartyRoleMapper;
import com.pdev.user_service.model.AuditData;
import com.pdev.user_service.model.authorizePartyRole.AuthorizePartyRole;
import com.pdev.user_service.repository.authorizePartyRole.AuthorizePartyRoleRepository;
import com.pdev.user_service.service.authorizePartyRole.AuthorizePartyRoleService;
import com.pdev.user_service.service.validation.authorizePartyRole.ValidateAuthorizePartyRole;
import com.pdev.user_service.util.CommonResponse;
import com.pdev.user_service.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service Implementation for managing {@link AuthorizePartyRole}.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizePartyRoleServiceImpl implements AuthorizePartyRoleService {

    private final ValidateAuthorizePartyRole validateAuthorizePartyRole;
    private final AuthorizePartyRoleRepository authorizePartyRoleRepository;
    private final CommonUtil commonUtil;
    private final AuthorizePartyRoleMapper authorizePartyRoleMapper;

    /**
     * This method is allowed to create or update authorize party role
     *
     * @param authorizePartyRoleRequest {@link AuthorizePartyRoleRequestDTO} - authorize party role request
     * @return {@link CommonResponse} - authorize party role created or updated response
     * @author @maleeshasa
     */
    @Override
    public CommonResponse createOrUpdate(AuthorizePartyRoleRequestDTO authorizePartyRoleRequest) {
        log.info("AuthorizePartyRoleServiceImpl.createOrUpdate() => started.");
        AuthorizePartyRole authorizePartyRole = new AuthorizePartyRole();
        String message;

        // Validate authorize party role request
        validateAuthorizePartyRole.validateAuthorizePartyRole(authorizePartyRoleRequest);

        if (authorizePartyRoleRequest.getId() != null) {
            message = "Authorize party role is updated.";
            authorizePartyRole = authorizePartyRoleRepository.findById(authorizePartyRoleRequest.getId())
                    .orElseThrow(() -> new RecordNotFoundException("Authorize party role is not exists."));
            authorizePartyRole.getAuditData().setUpdatedBy(commonUtil.getUsername());
            authorizePartyRole.getAuditData().setUpdatedOn(LocalDateTime.now());

        } else {
            message = "Authorize party role is created.";
            authorizePartyRole.setAuditData(new AuditData(LocalDateTime.now(), commonUtil.getUsername()));
        }

        AuthorizePartyRole mappedEntity = authorizePartyRoleMapper.mapToEntity(authorizePartyRole, authorizePartyRoleRequest);
        try {
            return new CommonResponse(
                    HttpStatus.OK,
                    message,
                    authorizePartyRoleMapper.mapToDTO(new AuthorizePartyRoleResponseDTO(), authorizePartyRoleRepository.save(mappedEntity))
            );
        } catch (Exception e) {
            log.error("Error while saving authorize party role. Error: ", e);
            return new CommonResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Authorize party role save failed.", null);
        }
    }

    /**
     * This method is allowed to get all authorize party roles
     *
     * @return {@link CommonResponse} - all authorize party roles
     * @author @maleeshasa
     */
    @Override
    public CommonResponse getAll() {
        List<AuthorizePartyRole> authorizePartyRoles = authorizePartyRoleRepository.findAll()
                .stream()
                .filter(a -> a.getActive().equals(Boolean.TRUE))
                .toList();
        if (!authorizePartyRoles.isEmpty()) {
            return new CommonResponse(
                    HttpStatus.OK, "Authorize party roles are exists.", authorizePartyRoleMapper.mapToList(authorizePartyRoles)
            );
        } else {
            return new CommonResponse(
                    HttpStatus.NO_CONTENT, "Authorize party roles not found.", null
            );
        }
    }

    /**
     * This method is allowed to get all authorize party roles with pagination
     *
     * @param of {@link PageRequest} - page request
     * @return {@link CommonResponse} - authorize party roles with pagination
     * @author @maleeshasa
     */
    @Override
    public CommonResponse getAllWithPage(PageRequest of) {
        Page<AuthorizePartyRole> authorizePartyRoles = authorizePartyRoleRepository.findAll(of);
        if (!authorizePartyRoles.isEmpty()) {
            PageResponse pageResponse = PageResponse.builder()
                    .currentPage(authorizePartyRoles.getNumber())
                    .totalPages(authorizePartyRoles.getTotalPages())
                    .totalElements(authorizePartyRoles.getTotalElements())
                    .dataList(authorizePartyRoleMapper.mapToList(authorizePartyRoles.getContent())).build();

            return new CommonResponse(
                    HttpStatus.OK, "Authorize party roles are exists.", pageResponse
            );
        } else {
            return new CommonResponse(
                    HttpStatus.NO_CONTENT, "Authorize party roles not found.", null
            );
        }
    }

    @Override
    public CommonResponse getById(int id) {
        AuthorizePartyRole authorizePartyRole = authorizePartyRoleRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Authorize party role is not exists."));
        return new CommonResponse(
                HttpStatus.OK, "Authorize party role is exists.", authorizePartyRoleMapper.mapToDTO(new AuthorizePartyRoleResponseDTO(), authorizePartyRole)
        );
    }

    @Override
    public CommonResponse deleteById(int id) {
        AuthorizePartyRole role = authorizePartyRoleRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Authorize party role not found."));
        role.setActive(Boolean.FALSE);
        authorizePartyRoleRepository.save(role);
        return new CommonResponse(
                HttpStatus.OK, "Authorize party role is deleted.", null
        );
    }
}
