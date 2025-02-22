package com.pdev.user_service.dto.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author @maleeshasa
 * @Date 2024/02/22
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ComponentDTO {
    private String key;
    private String name;
    private String status;
}
