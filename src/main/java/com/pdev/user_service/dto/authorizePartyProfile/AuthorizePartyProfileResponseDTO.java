package com.pdev.user_service.dto.authorizePartyProfile;

import com.pdev.user_service.dto.authorizeParty.AuthorizePartyResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthorizePartyProfileResponseDTO {
    private Integer authorizePartyId;
    private AuthorizePartyResponseDTO authorizeParty;
    private String authorizePartyRoles;
}
