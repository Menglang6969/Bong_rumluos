package com.menglang.bong_rumluos.Bong_rumluos.entities;

import com.menglang.bong_rumluos.Bong_rumluos.entities.base.BaseAuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "files")
public class File extends BaseAuditEntity<Long> {

    @Column(length = 100)
    private String name;

    @Column(name = "original_name", length = 150)
    private String originalName;

    @Column(length = 30)
    private String type;

    @Column(name = "size")
    private Long size;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        File file = (File) object;
        return size == file.size && Objects.equals(name, file.name) && Objects.equals(originalName, file.originalName) && Objects.equals(type, file.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, originalName, type, size);
    }

}
