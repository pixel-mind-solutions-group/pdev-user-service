package com.pdev.user_service.service.authorizeParty;

import com.pdev.user_service.dto.authorizeParty.AuthorizePartyRequestDTO;
import com.pdev.user_service.util.CommonResponse;
import org.springframework.data.domain.PageRequest;

/**
 * This interface is used to manage authorize party
 *
 * @author @maleeshasa
 * @version 1.0
 */
public interface AuthorizePartyService {

    /**
     * This method is used to create or update authorize party
     *
     * @param authorizePartyRequest {@link AuthorizePartyRequestDTO} - authorize party request
     * @return {@link CommonResponse} - authorize party created or updated response
     * @author @maleeshasa
     */
    CommonResponse createOrUpdate(AuthorizePartyRequestDTO authorizePartyRequest);

    /**
     * This method is used to get all authorize parties
     *
     * @return {@link CommonResponse} - all authorize parties
     * @author @maleeshasa
     */
    CommonResponse getAll();

    /**
     * This method is used to get all authorize parties with pagination
     *
     * @param of {@link PageRequest} - page request
     * @return {@link CommonResponse} - all authorize parties
     * @author @maleeshasa
     */
    CommonResponse getAllWithPage(PageRequest of);

    CommonResponse getById(int id);

    CommonResponse deleteById(int id);
}
