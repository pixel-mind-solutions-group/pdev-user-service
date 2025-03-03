package com.pdev.user_service.service.impl.authorizePartyRole;

import com.pdev.user_service.dto.authorizePartyRole.AuthorizePartyRoleRequestDTO;
import com.pdev.user_service.service.authorizePartyRole.AuthorizePartyRoleService;
import com.pdev.user_service.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizePartyRoleServiceImpl implements AuthorizePartyRoleService {

    @Override
    public CommonResponse createOrUpdate(AuthorizePartyRoleRequestDTO authorizePartyRoleRequest) {
        return null;
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
