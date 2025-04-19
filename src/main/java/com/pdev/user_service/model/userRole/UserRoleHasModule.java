package com.pdev.user_service.model.userRole;

import com.pdev.user_service.model.AuditData;
import com.pdev.user_service.model.module.Module;
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
@Table(name = "user_role_has_module")
public class UserRoleHasModule {
    @Id
    @Column(name = "id_user_role_has_module")
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

    @ManyToOne
    @JoinColumn(name = "user_role_id_user_role")
    private UserRole userRole;

    @ManyToOne
    @JoinColumn(name = "module_id_module")
    private Module module;

    @OneToMany(mappedBy = "userRoleHasModule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRoleHasModuleHasComponent> userRoleHasModuleHasComponents = new ArrayList<>();
}
