package com.menglang.bong_rumluos.Bong_rumluos.entities;

import com.menglang.bong_rumluos.Bong_rumluos.entities.base.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "phone"})
},
        indexes = {
                @Index(name = "idx_customer_name", columnList = "name"),
                @Index(name = "idx_phone", columnList = "phone")
        }
)

public class Customer extends BaseAuditEntity<Long> {

    @Column(length = 50)
    private String name;

    @Column(length = 30, unique = true)
    private String phone;

    private String address;

    private String occupation;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "doc_urls")
    private List<String> docsUrls;


}
