package com.pdev.user_service.model.authorizeParty;

import com.pdev.user_service.model.AuditData;
import jakarta.persistence.*;
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
@Entity
@Table(name = "authorize_party")
public class AuthorizeParty {
    @Id
    @Column(name = "id_authorize_party")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "party", nullable = false, length = 32)
    private String party;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "createdBy", column = @Column(name = "created_by")),
            @AttributeOverride(name = "createdOn", column = @Column(name = "created_on")),
            @AttributeOverride(name = "updatedBy", column = @Column(name = "updated_by")),
            @AttributeOverride(name = "updatedOn", column = @Column(name = "updated_on"))
    })
    private AuditData auditData;

    @OneToMany(mappedBy = "authorizeParty", cascade = {CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<AuthorizePartyHasAuthorizePartyRole> authorizePartyHasPartyRoles = new ArrayList<>();
}
