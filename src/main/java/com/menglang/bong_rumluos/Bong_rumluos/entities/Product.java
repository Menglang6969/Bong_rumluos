package com.menglang.bong_rumluos.Bong_rumluos.entities;

import com.menglang.bong_rumluos.Bong_rumluos.entities.base.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "products",
        indexes = {
                @Index(name = "idx_name", columnList = "name"),
                @Index(name = "idx_identify", columnList = "identify1,identify2")
        }
)
public class Product extends BaseAuditEntity<Long> {

    @Column(length = 50)
    private String name;

    @Column(length = 100)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "image_url")
    private String imageUrl;

    private String color;

    @Column(length = 50,unique = true)
    private String identify1;

    @Column(length = 50,unique = true)
    private String identify2;

    @Column(length = 50,unique = true)
    private String identify3;
    @Column(length = 50,unique = true)
    private String identify4;

}
