package com.menglang.bong_rumluos.Bong_rumluos.entities;

import com.menglang.bong_rumluos.Bong_rumluos.entities.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Table(name = "permissions")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Permission extends BaseEntity<Long> {

    @Column(length = 50,unique = true)
    private String name;

    @Column(length = 100)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "permissions")
    @Builder.Default
    private Set<Role> roles=new HashSet<>();
}
