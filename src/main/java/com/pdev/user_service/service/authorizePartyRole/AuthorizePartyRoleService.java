package com.pdev.user_service.service.authorizePartyRole;

import com.pdev.user_service.dto.authorizePartyRole.AuthorizePartyRoleRequestDTO;
import com.pdev.user_service.util.CommonResponse;
import org.springframework.data.domain.PageRequest;

/**
 * This interface is used to manage authorize party role
 *
 * @author @maleeshasa
 * @version 1.0
 */
public interface AuthorizePartyRoleService {

    /**
     * This method is allowed to create or update authorize party role
     *
     * @param authorizePartyRoleRequest {@link AuthorizePartyRoleRequestDTO} - authorize party role request
     * @return {@link CommonResponse} - authorize party role created or updated response
     * @author @maleeshasa
     */
    CommonResponse createOrUpdate(AuthorizePartyRoleRequestDTO authorizePartyRoleRequest);

    /**
     * This method is allowed to get all authorize party roles
     *
     * @return {@link CommonResponse} - all authorize party roles
     * @author @maleeshasa
     */
    CommonResponse getAll();

    /**
     * This method is allowed to get all authorize party roles with pagination
     *
     * @param of {@link PageRequest} - page request
     * @return {@link CommonResponse} - authorize party roles with pagination
     * @author @maleeshasa
     */
    CommonResponse getAllWithPage(PageRequest of);

    CommonResponse getById(int id);

    CommonResponse deleteById(int id);
}
