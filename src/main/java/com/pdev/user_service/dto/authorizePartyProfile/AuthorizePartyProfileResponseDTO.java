package com.pdev.user_service.dto.authorizePartyProfile;

import com.pdev.user_service.dto.authorizeParty.AuthorizePartyResponseDTO;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorizePartyProfileResponseDTO {
    private Integer authorizePartyId;
    private AuthorizePartyResponseDTO authorizeParty;
    private String authorizePartyRoles;
    private List<String> authorizePartyRolesIdList;
}
