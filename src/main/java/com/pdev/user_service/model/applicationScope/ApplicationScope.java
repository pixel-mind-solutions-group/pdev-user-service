package com.pdev.user_service.model.applicationScope;

import com.pdev.user_service.model.AuditData;
import com.pdev.user_service.model.component.Component;
import com.pdev.user_service.model.componentElement.ComponentElement;
import com.pdev.user_service.model.module.Module;
import com.pdev.user_service.model.userRole.UserRole;
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
@Table(name = "application_scope")
public class ApplicationScope {
    @Id
    @Column(name = "id_application_scope")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "scope", nullable = false, length = 32)
    private String scope;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "unique_id", nullable = false) // uuid
    private String uniqueId;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "createdBy", column = @Column(name = "created_by")),
            @AttributeOverride(name = "createdOn", column = @Column(name = "created_on")),
            @AttributeOverride(name = "updatedBy", column = @Column(name = "updated_by")),
            @AttributeOverride(name = "updatedOn", column = @Column(name = "updated_on"))
    })
    private AuditData auditData;

    @OneToMany(mappedBy = "applicationScope", fetch = FetchType.LAZY)
    private List<UserRole> userRoles = new ArrayList<>();

    @OneToMany(mappedBy = "applicationScope", fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
    private List<Module> modules = new ArrayList<>();

    @OneToMany(mappedBy = "applicationScope", fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
    private List<Component> components = new ArrayList<>();

    @OneToMany(mappedBy = "applicationScope", fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
    private List<ComponentElement> componentElements = new ArrayList<>();
}
