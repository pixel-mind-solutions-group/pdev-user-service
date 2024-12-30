package com.pdev.user_service.model.user;

import com.pdev.user_service.model.applicationScope.ApplicationScope;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Getter
@Setter
@Entity
@Table(name = "user_login_registry")
public class UserLoginRegistry {
    @Id
    @Column(name = "id_user_login_registry")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "login_or_logout", nullable = false)
    private int loginOrLogout;

    @Column(name = "last_updated_date_time", nullable = false)
    private LocalDateTime lastUpdatedDateTime;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_account_id_user")
    private User user;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "application_scope_id_application_scope")
    private ApplicationScope applicationScope;
}
