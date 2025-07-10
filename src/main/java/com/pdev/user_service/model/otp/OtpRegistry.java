package com.pdev.user_service.model.otp;

import com.pdev.user_service.model.applicationScope.ApplicationScope;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "otp_registry")
public class OtpRegistry {
    @Id
    @Column(name = "id_otp_registry")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "application_scope_id_application_scope", nullable = false)
    private ApplicationScope applicationScope;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "otp", length = 6)
    private String otp;

    @Column(name = "ref_value")
    private String refValue;

    @Column(name = "remark")
    private String remark;
}
