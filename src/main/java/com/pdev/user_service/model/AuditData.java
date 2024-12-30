package com.pdev.user_service.model;

import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDateTime;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class AuditData {

    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime createdOn;

    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime updatedOn;

    private String createdBy;

    private String updatedBy;

    public AuditData(LocalDateTime createdOn, String createdBy) {
        this.createdOn = createdOn;
        this.createdBy = createdBy;
    }

    public AuditData(String updatedBy, LocalDateTime updatedOn) {
        this.updatedBy = updatedBy;
        this.updatedOn = updatedOn;
    }
}
