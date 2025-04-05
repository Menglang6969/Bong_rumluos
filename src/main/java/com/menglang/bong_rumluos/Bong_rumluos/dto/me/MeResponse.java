package com.menglang.bong_rumluos.Bong_rumluos.dto.me;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class MeResponse {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private List<String> roles;
    private List<String> permissions;
}
