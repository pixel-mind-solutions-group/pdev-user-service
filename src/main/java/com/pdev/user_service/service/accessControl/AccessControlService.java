package com.pdev.user_service.service.accessControl;

import com.pdev.user_service.dto.accessControl.AccessControlRequestDTO;
import com.pdev.user_service.util.CommonResponse;
import org.springframework.data.domain.PageRequest;

public interface AccessControlService {

    CommonResponse createOrUpdate(AccessControlRequestDTO accessControlRequest);

    CommonResponse getAllWithPagination(PageRequest of);

    CommonResponse getById(int id);
}
