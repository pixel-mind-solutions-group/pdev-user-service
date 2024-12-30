package com.pdev.user_service.model.user;

import com.pdev.user_service.model.AuditData;
import com.pdev.user_service.model.authorizeParty.AuthorizeParty;
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
@Table(name = "user_has_authorize_party")
public class UserHasAuthorizeParty {
    @Id
    @Column(name = "id_user_has_authorize_party")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @JoinColumn(name = "user_id_user", nullable = false)
    @ManyToOne
    private User user;

    @JoinColumn(name = "authorize_party_id_authorize_party", nullable = false)
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private AuthorizeParty authorizeParty;
}
