package com.pdev.user_service.dto.accessControl;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AccessControlRequestDTO {
    private String applicationScope;
    private List<Integer> componentElements = new ArrayList<>();
    private List<Integer> components = new ArrayList<>();
    private List<Integer> modules = new ArrayList<>();
    private Integer userRole;
}
