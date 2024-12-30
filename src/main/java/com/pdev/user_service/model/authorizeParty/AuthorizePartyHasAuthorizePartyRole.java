package com.pdev.user_service.model.authorizeParty;

import com.pdev.user_service.model.AuditData;
import com.pdev.user_service.model.authorizePartyRole.AuthorizePartyRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Getter
@Setter
@Entity
@Table(name = "authorize_party_has_authorize_party_role")
public class AuthorizePartyHasAuthorizePartyRole {
    @Id
    @Column(name = "id_authorize_party_has_authorize_party_role")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "createdBy", column = @Column(name = "created_by")),
            @AttributeOverride(name = "createdOn", column = @Column(name = "created_on")),
            @AttributeOverride(name = "updatedBy", column = @Column(name = "updated_by")),
            @AttributeOverride(name = "updatedOn", column = @Column(name = "updated_on"))
    })
    private AuditData auditData;

    @JoinColumn(name = "authorize_party_id_authorize_party", nullable = false)
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private AuthorizeParty authorizeParty;

    @JoinColumn(name = "authorize_party_role_id_authorize_party_role", nullable = false)
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private AuthorizePartyRole authorizePartyRole;
}
