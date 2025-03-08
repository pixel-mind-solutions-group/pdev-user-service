package com.pdev.user_service.service.componentElement;

import com.pdev.user_service.dto.component.ComponentRequestDTO;
import com.pdev.user_service.dto.componentElement.ComponentElementRequestDTO;
import com.pdev.user_service.util.CommonResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

/**
 * @author @maleeshasa
 * @Date 2025/02/22
 */
public interface ComponentElementService {

    /**
     * This method is allowed to create or update component element
     *
     * @param componentElementRequest {@link ComponentRequestDTO} - component element request
     * @return {@link ResponseEntity <CommonResponse>} - component element created or updated response
     * @author @maleeshasa
     */
    CommonResponse createOrUpdate(ComponentElementRequestDTO componentElementRequest);

    /**
     * This method is allowed to get all component elements
     *
     * @return {@link ResponseEntity<CommonResponse>} - all components elements
     * @author @maleeshasa
     */
    CommonResponse getAll();

    /**
     * This method is allowed to get all component elements with pagination
     *
     * @param pageRequest {@link PageRequest} - page request
     * @return {@link CommonResponse} - all components elements
     * @author @maleeshasa
     */
    CommonResponse getAllWithPage(PageRequest pageRequest);

    CommonResponse deleteById(int id);

    CommonResponse getById(Integer id);
}
