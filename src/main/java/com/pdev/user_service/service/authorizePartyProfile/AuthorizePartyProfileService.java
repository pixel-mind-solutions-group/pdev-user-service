package com.pdev.user_service.service.authorizePartyProfile;

import com.pdev.user_service.dto.authorizePartyProfile.AuthorizePartyProfileRequestDTO;
import com.pdev.user_service.util.CommonResponse;
import org.springframework.data.domain.PageRequest;


public interface AuthorizePartyProfileService {

    CommonResponse createOrUpdate(AuthorizePartyProfileRequestDTO authorizePartyProfileRequest);

    CommonResponse getAll();

    CommonResponse getAllWithPage(PageRequest of);

    CommonResponse getAllByAuthParty(Integer id);

    CommonResponse deleteByAuthParty(int id);
}
