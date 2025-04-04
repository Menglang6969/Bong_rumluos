package com.menglang.bong_rumluos.Bong_rumluos.dto.role;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.menglang.bong_rumluos.Bong_rumluos.dto.BaseAuditDTO;
import com.menglang.bong_rumluos.Bong_rumluos.dto.permission.PermissionResponse;
import com.menglang.bong_rumluos.Bong_rumluos.dto.permission.PermissionShortResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@JsonPropertyOrder({"id", "name", "description","permissions", "createdAt", "createdBy", "updatedAt", "updatedBy"})
public class RoleResponse extends BaseAuditDTO {
    private String name;
    private String description;
    private List<PermissionShortResponse> permissions;

    public RoleResponse(Long id, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
        super(id, createdAt, createdBy, updatedAt, updatedBy);
    }
}
