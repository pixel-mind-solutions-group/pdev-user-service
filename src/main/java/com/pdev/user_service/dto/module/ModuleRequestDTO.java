package com.pdev.user_service.dto.module;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author @maleeshasa
 * @Date 2025/02/22
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModuleRequestDTO {
    private Integer id;
    private String key;
    private String module;
    private String status;
    private String applicationScope;
    private List<ModuleDTO> modules = new ArrayList<>();

    public ModuleRequestDTO(String key, String module, String status) {
        this.key = key;
        this.module = module;
        this.status = status;
    }
}
