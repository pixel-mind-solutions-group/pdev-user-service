package com.pdev.user_service.model.componentElement;

import com.pdev.user_service.model.AuditData;
import com.pdev.user_service.model.applicationScope.ApplicationScope;
import com.pdev.user_service.model.component.Component;
import com.pdev.user_service.model.module.Module;
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
@Table(name = "component_element")
public class ComponentElement {
    @Id
    @Column(name = "id_element")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "element_name", nullable = false)
    private String elementName;

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

    @JoinColumn(name = "module_id_module", nullable = false)
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Module module;

    @JoinColumn(name = "application_scope_id_application_scope", nullable = false)
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private ApplicationScope applicationScope;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "component_id_component")
    private Component component;
}
