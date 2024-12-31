package com.menglang.bong_rumluos.Bong_rumluos.dto.permission;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.menglang.bong_rumluos.Bong_rumluos.dto.BaseAuditDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonPropertyOrder({"id", "name", "description", "createdAt", "createdBy", "updatedAt", "updatedBy"})
public class PermissionResponse extends BaseAuditDTO {

    private String name;
    private String description;

    public PermissionResponse(Long id, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
        super(id, createdAt, createdBy, updatedAt, updatedBy);
    }
}
