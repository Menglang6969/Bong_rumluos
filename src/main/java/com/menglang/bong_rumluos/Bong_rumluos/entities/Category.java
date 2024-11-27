package com.menglang.bong_rumluos.Bong_rumluos.entities;

import com.menglang.bong_rumluos.Bong_rumluos.entities.base.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder
@Setter
@Getter
@Table(name = "categories")
public class Category extends BaseAuditEntity<Long> {

    @Column(length = 50,unique = true)
    private String name;

    @Column(length = 100)
    private String description;

    @Column(length = 40)
    private String color;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "parent_id"
    )
    private Category parent;

}
