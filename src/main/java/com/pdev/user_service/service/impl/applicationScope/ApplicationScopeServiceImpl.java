package com.pdev.user_service.service.impl.applicationScope;

import com.pdev.user_service.controller.response.PageResponse;
import com.pdev.user_service.dto.applicationScope.ApplicationScopeRequestDTO;
import com.pdev.user_service.dto.applicationScope.ApplicationScopeResponseDTO;
import com.pdev.user_service.exception.RecordNotFoundException;
import com.pdev.user_service.mapper.applicationScope.ApplicationScopeMapper;
import com.pdev.user_service.model.AuditData;
import com.pdev.user_service.model.applicationScope.ApplicationScope;
import com.pdev.user_service.repository.applicationScope.ApplicationScopeRepository;
import com.pdev.user_service.service.applicationScope.ApplicationScopeService;
import com.pdev.user_service.service.validation.CommonValidation;
import com.pdev.user_service.service.validation.applicationScope.ValidateApplicationScope;
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
import java.util.UUID;

/**
 * @author @maleeshasa
 * @Date 2025/02/21
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ApplicationScopeServiceImpl implements ApplicationScopeService {

    private final ApplicationScopeRepository applicationScopeRepository;
    private final ValidateApplicationScope validateApplicationScope;
    private final ApplicationScopeMapper applicationScopeMapper;
    private final CommonUtil commonUtil;

    /**
     * This method is allowed to create or update application scope
     *
     * @param applicationScopeRequest {@link ApplicationScopeRequestDTO} - application scope request
     * @return {@link CommonResponse} - created or updated response
     * @author @maleeshasa
     */
    @Override
    public CommonResponse createOrUpdateApplicationScope(ApplicationScopeRequestDTO applicationScopeRequest) {
        log.info("ApplicationScopeServiceImpl.createOrUpdateApplicationScope() => started.");

        // Validate application scope
        validateApplicationScope.validateApplicationScope(applicationScopeRequest);

        String message;
        ApplicationScope applicationScope = new ApplicationScope();
        if (!CommonValidation.stringNullValidation(applicationScopeRequest.getUniqueId())) {
            log.info("Application scope is updating...");
            message = "Application scope is updated.";
            applicationScope = applicationScopeRepository.findByUniqueId(applicationScopeRequest.getUniqueId());
            applicationScope.getAuditData().setUpdatedOn(LocalDateTime.now());
            applicationScope.getAuditData().setUpdatedBy(commonUtil.getUsername());

        } else {
            log.info("Application scope is creating...");
            message = "Application scope is created.";

            String uuid;
            do {
                uuid = UUID.randomUUID().toString();
            } while (!validateApplicationScope.uniqueUUID(uuid));

            applicationScope.setUniqueId(uuid);
            applicationScope.setAuditData(new AuditData(LocalDateTime.now(), commonUtil.getUsername()));
        }

        ApplicationScope mappedScope = applicationScopeMapper.mapToEntity(applicationScope, applicationScopeRequest);
        CommonResponse commonResponse = new CommonResponse();
        try {
            commonResponse.setData(
                    applicationScopeMapper.mapToDTO(new ApplicationScopeResponseDTO(),
                            applicationScopeRepository.save(mappedScope)));
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setMessage(message);
            log.info("ApplicationScopeServiceImpl.createOrUpdateApplicationScope() => ended.");
            return commonResponse;

        } catch (Exception e) {
            log.error("Error while saving application scope. Error: {}", e.getMessage());
            commonResponse.setData(null);
            commonResponse.setMessage("Application scope save failed.");
            commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return commonResponse;
        }
    }

    /**
     * This method is allowed to fetch all application scopes
     *
     * @return {@link CommonResponse} - fetched application scopes response
     * @author @maleeshasa
     */
    @Override
    public CommonResponse getAll() {
        log.info("ApplicationScopeController.getAll() => started.");
        CommonResponse commonResponse = new CommonResponse();

        List<ApplicationScope> applicationScopes = applicationScopeRepository.findAll().stream()
                .filter(ApplicationScope::getActive)
                .toList();

        if (!applicationScopes.isEmpty()) {
            log.info("Application scopes are exists.");
            commonResponse.setData(applicationScopeMapper.mapToDTOList(applicationScopes));
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setMessage("Application scopes are exists.");
            return commonResponse;

        } else {
            log.info("Application scopes are not exists.");
            commonResponse.setData(null);
            commonResponse.setStatus(HttpStatus.NO_CONTENT);
            commonResponse.setMessage("Application scopes are not exists.");
            return commonResponse;
        }
    }

    /**
     * This method is allowed to fetch all application scopes with pagination
     *
     * @param pageRequest {@link PageRequest} - page request
     * @return {@link CommonResponse} - fetched application scopes response
     * @author @maleeshasa
     */
    @Override
    public CommonResponse getAllWithPagination(PageRequest pageRequest) {
        log.info("ApplicationScopeController.getAllWithPagination() => started.");
        CommonResponse commonResponse = new CommonResponse();

        Page<ApplicationScope> applicationScopes = applicationScopeRepository.findAll(pageRequest);

        if (!applicationScopes.isEmpty()) {
            log.info("App scopes are exists.");
            // Constructing page response as pagination
            PageResponse pageResponse = PageResponse.builder()
                    .totalPages(applicationScopes.getTotalPages())
                    .totalElements(applicationScopes.getTotalElements())
                    .currentPage(applicationScopes.getNumber())
                    .dataList(applicationScopeMapper.mapToDTOList(applicationScopes.getContent())).build();

            commonResponse.setData(pageResponse);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setMessage("Application scopes are exists.");
            return commonResponse;

        } else {
            log.info("App scopes are not exists.");
            commonResponse.setData(null);
            commonResponse.setStatus(HttpStatus.NO_CONTENT);
            commonResponse.setMessage("Application scopes are not exists.");
            return commonResponse;
        }
    }

    @Override
    public CommonResponse deleteById(int id) {
        ApplicationScope scope = applicationScopeRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Application scope not found."));
        applicationScopeRepository.delete(scope);
        return new CommonResponse(
                HttpStatus.OK, "Application scope is deleted.", null
        );
    }
}
