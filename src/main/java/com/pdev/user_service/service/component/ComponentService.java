package com.pdev.user_service.service.component;

import com.pdev.user_service.dto.component.ComponentRequestDTO;
import com.pdev.user_service.util.CommonResponse;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * @author @maleeshasa
 * @Date 2024/02/22
 */
public interface ComponentService {

    /**
     * This method is allowed to create or update component
     *
     * @param componentRequest {@link ComponentRequestDTO} - component request
     * @return {@link CommonResponse} - component created or updated response
     * @author @maleeshasa
     */
    CommonResponse createOrUpdate(ComponentRequestDTO componentRequest);

    /**
     * This method is allowed to get all components
     *
     * @return {@link CommonResponse} - all components
     * @author @maleeshasa
     */
    CommonResponse getAll();

    /**
     * This method is allowed to get all components with pagination
     *
     * @param pageRequest {@link PageRequest} - page request
     * @return {@link CommonResponse} - all components
     * @author @maleeshasa
     */
    CommonResponse getAllWithPage(PageRequest pageRequest);

    /**
     * This method is allowed to get all components by scope and module
     *
     * @param scope  {@link String} - scope uuid
     * @param module {@link int} - module id
     * @return {@link CommonResponse} - all components by scope and module
     * @author @maleeshasa
     */
    CommonResponse getByScopeAndModule(String scope, Integer module);

    CommonResponse deleteById(int id);

    CommonResponse getById(Integer id);

    CommonResponse getByScopeAndModules(String scope, List<Integer> modules);
}
