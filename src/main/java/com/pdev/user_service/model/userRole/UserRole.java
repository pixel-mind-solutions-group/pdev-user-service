package com.pdev.user_service.model.userRole;

import com.pdev.user_service.model.AuditData;
import com.pdev.user_service.model.applicationScope.ApplicationScope;
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
@Table(name = "user_role")
public class UserRole {
    @Id
    @Column(name = "id_user_role")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "role", nullable = false, length = 32)
    private String role;

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

    @ManyToOne
    @JoinColumn(name = "application_scope_id_application_scope")
    private ApplicationScope applicationScope;

    @OneToMany(mappedBy = "userRole", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserRoleHasModule> userRoleHasModules = new ArrayList<>();
}
