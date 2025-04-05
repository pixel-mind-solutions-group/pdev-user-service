package com.pdev.user_service.service.impl.userRole;

import com.pdev.user_service.controller.response.PageResponse;
import com.pdev.user_service.dto.userRole.UserRoleRequestDTO;
import com.pdev.user_service.dto.userRole.UserRoleResponseDTO;
import com.pdev.user_service.exception.BaseException;
import com.pdev.user_service.exception.RecordNotFoundException;
import com.pdev.user_service.mapper.userRole.UserRoleMapper;
import com.pdev.user_service.model.AuditData;
import com.pdev.user_service.model.userRole.UserRole;
import com.pdev.user_service.repository.user.UserHasApplicationScopeHasUserRoleRepository;
import com.pdev.user_service.repository.userRole.UserRoleRepository;
import com.pdev.user_service.service.userRole.UserRoleService;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserHasApplicationScopeHasUserRoleRepository userHasApplicationScopeHasUserRoleRepository;
    private final UserRoleMapper userRoleMapper;
    private final UserRoleRepository userRoleRepository;
    private final CommonUtil commonUtil;

    @Override
    public CommonResponse createOrUpdate(UserRoleRequestDTO userRoleRequest) {
        UserRole userRole = new UserRole();
        String message;
        if (userRoleRequest.getId() != null) {
            message = "User role updated.";
            userRole = userRoleRepository.findById(userRoleRequest.getId())
                    .orElseThrow(() -> new RecordNotFoundException("User role not found."));
            userRole.setAuditData(new AuditData(commonUtil.getUsername(), LocalDateTime.now()));

        } else {
            message = "User role created.";
            userRole.setAuditData(new AuditData(LocalDateTime.now(), commonUtil.getUsername()));
        }
        UserRole mappedEntity = userRoleMapper.mapToEntity(userRole, userRoleRequest);

        try {
            return new CommonResponse(
                    HttpStatus.OK,
                    message,
                    userRoleMapper.mapToDTO(new UserRoleResponseDTO(), userRoleRepository.save(mappedEntity))
            );
        } catch (Exception e) {
            log.error("Error while saving user role. Error: ", e);
            return new CommonResponse(HttpStatus.INTERNAL_SERVER_ERROR, "User role save failed.", null);
        }
    }

    @Override
    public CommonResponse getAll() {
        List<UserRole> userRoles = userRoleRepository.findAll()
                .stream()
                .filter(a -> a.getActive().equals(Boolean.TRUE))
                .toList();
        if (!userRoles.isEmpty()) {
            return new CommonResponse(
                    HttpStatus.OK, "User roles are exists.", userRoleMapper.mapToList(userRoles)
            );
        } else {
            return new CommonResponse(
                    HttpStatus.NO_CONTENT, "User roles not found.", null
            );
        }
    }

    @Override
    public CommonResponse getAllWithPage(PageRequest of) {
        Page<UserRole> userRoles = userRoleRepository.findAll(of);
        if (!userRoles.isEmpty()) {
            PageResponse pageResponse = PageResponse.builder()
                    .currentPage(userRoles.getNumber())
                    .totalPages(userRoles.getTotalPages())
                    .totalElements(userRoles.getTotalElements())
                    .dataList(userRoleMapper.mapToList(userRoles.getContent())).build();

            return new CommonResponse(
                    HttpStatus.OK, "User roles are exists.", pageResponse
            );
        } else {
            return new CommonResponse(
                    HttpStatus.NO_CONTENT, "User roles not found.", null
            );
        }
    }

    @Override
    public CommonResponse getById(int id) {
        UserRole userRole = userRoleRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("User role is not exists."));
        return new CommonResponse(
                HttpStatus.OK, "User role is exists.", userRoleMapper.mapToDTO(new UserRoleResponseDTO(), userRole)
        );
    }

    @Override
    public CommonResponse deleteById(int id) {
        UserRole role = userRoleRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("User role not found."));

        if (!userHasApplicationScopeHasUserRoleRepository.findByUserRole(role).isEmpty()) {
            throw new BaseException(400, "User role is already assigned to user.");
        }

        userRoleRepository.delete(role);
        return new CommonResponse(
                HttpStatus.OK, "User role is deleted.", null
        );
    }

    @Override
    public CommonResponse getByScope(String uuid) {
        List<UserRole> userRoles = userRoleRepository.findByApplicationScopeUniqueIdAndActive(uuid, Boolean.TRUE);
        if (userRoles.isEmpty()) {
            throw new RecordNotFoundException("User roles not found by app scope.");
        }
        return new CommonResponse(
                HttpStatus.OK, "User roles are exists.", userRoleMapper.mapToList(userRoles)
        );
    }
}
