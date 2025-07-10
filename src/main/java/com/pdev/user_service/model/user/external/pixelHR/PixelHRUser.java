package com.pdev.user_service.model.user.external.pixelHR;

import com.pdev.user_service.model.AuditData;
import com.pdev.user_service.model.user.UserHasApplicationScopeHasUserRole;
import com.pdev.user_service.model.user.UserHasAuthorizeParty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pixel_hr_user_account")
public class PixelHRUser {
    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email", nullable = false, length = 60)
    private String email;

    @Column(name = "is_email_verified", nullable = false)
    private Boolean isEmailVerified;

    @Column(name = "first_name", nullable = false, length = 32)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 32)
    private String lastName;

    @Column(name = "user_name", nullable = false, length = 32)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "non_locked", nullable = false)
    private Boolean accountNonLocked;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "fail_count", nullable = false)
    private Short failCount;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "createdBy", column = @Column(name = "created_by")),
            @AttributeOverride(name = "createdOn", column = @Column(name = "created_on")),
            @AttributeOverride(name = "updatedBy", column = @Column(name = "updated_by")),
            @AttributeOverride(name = "updatedOn", column = @Column(name = "updated_on"))
    })
    private AuditData auditData;

    @OneToMany(mappedBy = "pixelHRUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserHasAuthorizeParty> userHasAuthorizeParties = new ArrayList<>();

    @OneToMany(mappedBy = "pixelHRUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserHasApplicationScopeHasUserRole> userHasApplicationScopeHasUserRoles = new ArrayList<>();
}
