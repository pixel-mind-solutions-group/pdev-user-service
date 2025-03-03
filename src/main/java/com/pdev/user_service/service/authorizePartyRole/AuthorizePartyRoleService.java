package com.pdev.user_service.service.authorizePartyRole;

import com.pdev.user_service.dto.authorizePartyRole.AuthorizePartyRoleRequestDTO;
import com.pdev.user_service.util.CommonResponse;
import org.springframework.data.domain.PageRequest;

public interface AuthorizePartyRoleService {
    
    CommonResponse createOrUpdate(AuthorizePartyRoleRequestDTO authorizePartyRoleRequest);

    CommonResponse getAll();

    CommonResponse getAllWithPage(PageRequest of);
}
