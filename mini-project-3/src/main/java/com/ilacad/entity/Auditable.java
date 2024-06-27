package com.ilacad.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
public abstract class Auditable {

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private Instant createdDate;

    @UpdateTimestamp
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;
}
