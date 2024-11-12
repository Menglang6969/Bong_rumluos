package com.menglang.bong_rumluos.Bong_rumluos.entities;

import com.menglang.bong_rumluos.Bong_rumluos.entities.base.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder
@Table(name = "categories")
public class Category extends BaseAuditEntity<Long> {

    @Column(length = 50,unique = true)
    private String name;

    @Column(length = 100)
    private String description;

    @Column(length = 40)
    private String color;

    @ManyToOne()
    @JoinColumn(
            name = "parent_id"
    )
    private Category parentId;


    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Product> products=new HashSet<>();
}
