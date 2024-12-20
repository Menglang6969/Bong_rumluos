package com.menglang.bong_rumluos.Bong_rumluos.entities;
import com.menglang.bong_rumluos.Bong_rumluos.entities.base.BaseAuditEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "staffs")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Staff extends BaseAuditEntity<Long> {

    private String name;
    private String phone;
    private String description;

    @OneToOne
    private User user;
}
