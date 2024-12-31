package com.menglang.bong_rumluos.Bong_rumluos.dto.permission;

import lombok.Builder;

@Builder
public record PermissionRequest(
    String name,
    String description
) {
}
