package com.menglang.bong_rumluos.Bong_rumluos.entities.base;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDate;

@MappedSuperclass
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseAuditEntity <T extends Serializable> extends BaseEntity<T>{

//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at",nullable = false,updatable = false)
    @CreatedDate
    private LocalDate createdAt;


    @Column(name = "created_by",updatable = false,nullable = false,length = 40)
    @CreatedBy
    private String createdBy;

    @LastModifiedDate
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at",insertable = false)
    private LocalDate updatedAt;

    @LastModifiedBy
    @Column(name = "updated_by",insertable = false)
    private String updatedBy;


}
