package com.menglang.bong_rumluos.Bong_rumluos.dto.role;

import lombok.Builder;

import java.util.List;

@Builder
public record RoleRequest(
        String name,
        String description,
        List<Long> permissions
) {

}
