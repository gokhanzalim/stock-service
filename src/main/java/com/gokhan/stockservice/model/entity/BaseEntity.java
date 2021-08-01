package com.gokhan.stockservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private LocalDateTime updatedDate;

    @Column(nullable = false)
    private String updateUser;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;

    @PreUpdate
    private void setModifyDateTime() {
        setUpdatedDate(LocalDateTime.now());
    }

    @PrePersist
    private void setCreatedDate() {
        setCreatedDate(LocalDateTime.now());
        setUpdatedDate(LocalDateTime.now());
    }
}
