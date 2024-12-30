package com.pdev.user_service.model.userRole;

import com.pdev.user_service.model.AuditData;
import com.pdev.user_service.model.component.Component;
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
@Table(name = "user_role_has_module_has_component")
public class UserRoleHasModuleHasComponent {
    @Id
    @Column(name = "id_user_role_has_module_has_component")
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

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_role_has_module_id_user_role_has_module")
    private UserRoleHasModule userRoleHasModule;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "component_id_component")
    private Component component;

    @OneToMany(mappedBy = "userRoleHasModuleHasComponent", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    private List<UserRoleHasModuleHasComponentHasElement> userRoleHasModuleHasComponentHasElements = new ArrayList<>();
}
