package com.pdev.user_service.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author @maleeshasa
 * @Date 2025/03/01
 */
@Setter
@Getter
@Builder
public class PageResponse {
    private int totalPages;
    private int currentPage;
    private long totalElements;
    private Object dataList;
}
