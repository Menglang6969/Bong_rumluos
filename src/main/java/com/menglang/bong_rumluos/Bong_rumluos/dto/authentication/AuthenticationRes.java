package com.menglang.bong_rumluos.Bong_rumluos.dto.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;
@Builder
public record AuthenticationRes(String username,
                                String firstname,
                                String lastname,
                                String email,
                                List<String> roles,
                                @JsonProperty("access_token") String accessToken,
                                @JsonProperty("refresh_token") String refreshToken) {
}
