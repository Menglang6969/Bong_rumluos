package com.menglang.bong_rumluos.Bong_rumluos.dto.customer;

import lombok.Builder;

@Builder
public record  CustomerBaseResponse(
        Long id,
        String name,
        String phone
) {
}
