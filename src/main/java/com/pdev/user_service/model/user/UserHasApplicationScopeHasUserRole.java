package com.pdev.user_service.model.user;

import com.pdev.user_service.model.AuditData;
import com.pdev.user_service.model.applicationScope.ApplicationScope;
import com.pdev.user_service.model.userRole.UserRole;
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
@Table(name = "user_has_application_scope_has_user_role")
public class UserHasApplicationScopeHasUserRole {
    @Id
    @Column(name = "id_user_has_application_scope_has_user_role")
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

    @Column(name = "active", nullable = false)
    private Boolean active;

    @JoinColumn(name = "user_id_user", nullable = false)
    @ManyToOne
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "application_scope_id_application_scope")
    private ApplicationScope applicationScope;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_role_id_user_role")
    private UserRole userRole;
}
