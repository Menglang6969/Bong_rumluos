package com.menglang.bong_rumluos.Bong_rumluos.entities;
import com.menglang.bong_rumluos.Bong_rumluos.entities.base.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Column(length = 50,unique = true)
    private String name;

    @Column(length = 100)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "image_url")
    private String imageUrl;

    private String color;

    @Column(name = "base_price")
    private BigDecimal basePrice;

    @Column(name = "sellPrice")
    private BigDecimal sellPrice;

    @Column(length = 50,unique = true)
    private String identify1;

    @Column(length = 50,unique = true)
    private String identify2;

    @Column(length = 50)
    private String identify3;
    @Column(length = 50)
    private String identify4;

    @DateTimeFormat
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}
