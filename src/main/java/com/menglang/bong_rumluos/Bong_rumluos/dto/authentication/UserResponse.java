package com.menglang.bong_rumluos.Bong_rumluos.dto.authentication;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class UserResponse {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private List<String> roles;
}
