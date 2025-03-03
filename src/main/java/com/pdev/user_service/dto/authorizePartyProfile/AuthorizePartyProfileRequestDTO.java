package com.pdev.user_service.dto.authorizePartyProfile;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AuthorizePartyProfileRequestDTO {
    private Integer id;
    private Integer authorizeParty;
    private List<Integer> authorizePartyRoles = new ArrayList<>();
}
