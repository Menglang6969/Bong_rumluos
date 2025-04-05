package com.menglang.bong_rumluos.Bong_rumluos.dto.authentication;

import lombok.Builder;

@Builder
public record AuthenticationReq(
        String username,
        String password
) {
}
