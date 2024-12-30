package com.pdev.user_service.model.userRole;

import com.pdev.user_service.model.AuditData;
import com.pdev.user_service.model.componentElement.ComponentElement;
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
@Table(name = "user_role_has_module_has_component_has_element")
public class UserRoleHasModuleHasComponentHasElement {
    @Id
    @Column(name = "id_user_role_has_module_has_component_has_element")
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
    @JoinColumn(name = "user_role_has_module_has_component_id_user_role_has_module_has_")
    private UserRoleHasModuleHasComponent userRoleHasModuleHasComponent;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "component_element_id_component_element")
    private ComponentElement componentElement;

    @Column(name = "editable", nullable = false)
    private Boolean editable;
}
