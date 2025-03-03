package com.pdev.user_service.dto.authorizeParty;

import com.pdev.user_service.dto.authorizePartyRole.AuthorizePartyRoleResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Getter
@Setter
public class AuthorizePartyRequestDTO {
    private Integer id;
    private String party;
    private String status;
    private List<AuthorizePartyRoleResponseDTO> authorizePartyRoles = new ArrayList<>();
}
