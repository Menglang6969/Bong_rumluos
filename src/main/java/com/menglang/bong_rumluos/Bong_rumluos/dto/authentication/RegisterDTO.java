package com.menglang.bong_rumluos.Bong_rumluos.dto.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@Builder
public record RegisterDTO(
        @NotBlank(message = "Firstname must not be Blank")
        @Size(min = 3, max = 20, message = "Firstname Length must be between 3 and 20 characters")
        String firstname,

        @NotBlank(message = "Lastname must not be Blank")
        @Size(min = 3, max = 20, message = "Lastname Length must be between 3 and 20 characters")
        String lastname,

        @NotBlank(message = "Username must not be Blank")
        @Size(min = 3, max = 20, message = "Username Length must be between 3 and 20 characters")
        String username,
        @Email
        String email,
        @NotBlank(message = "Phone must not be Blank")
//        @Size(min = 9, max = 15, message = "Phone Length must be between 9 and 14 characters")
//        String phone,
        @NotBlank(message = "Password must not be Blank")
//        @Size(min = , max = 15, message = "Password Length must be between 9 and 14 characters")
        String password,

        List<Long> roles
) {

}
