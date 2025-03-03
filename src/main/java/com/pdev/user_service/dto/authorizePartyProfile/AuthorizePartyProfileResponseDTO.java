package com.pdev.user_service.dto.authorizePartyProfile;

import com.pdev.user_service.dto.authorizeParty.AuthorizePartyResponseDTO;
import com.pdev.user_service.dto.authorizePartyRole.AuthorizePartyRoleResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthorizePartyProfileResponseDTO {
    private Integer id;
    private AuthorizePartyResponseDTO authorizeParty;
    private AuthorizePartyRoleResponseDTO authorizePartyRole;
}
