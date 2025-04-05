package com.pdev.user_service.service.userRole;

import com.pdev.user_service.dto.userRole.UserRoleRequestDTO;
import com.pdev.user_service.util.CommonResponse;
import org.springframework.data.domain.PageRequest;

public interface UserRoleService {

    CommonResponse createOrUpdate(UserRoleRequestDTO userRoleRequest);

    CommonResponse getAll();

    CommonResponse getAllWithPage(PageRequest of);

    CommonResponse getById(int id);

    CommonResponse deleteById(int id);

    CommonResponse getByScope(String uuid);
}
