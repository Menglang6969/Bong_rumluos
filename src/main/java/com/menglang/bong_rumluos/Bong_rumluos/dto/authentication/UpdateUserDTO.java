package com.menglang.bong_rumluos.Bong_rumluos.dto.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Setter;

import java.util.Set;

@Builder
@Setter
public class UpdateUserDTO {
    @NotBlank(message = "Firstname must not be Blank")
    @Size(min = 3, max = 20, message = "Firstname Length must be between 3 and 20 characters")
    String firstname;

    @NotBlank(message = "Lastname must not be Blank")
    @Size(min = 3, max = 20, message = "Lastname Length must be between 3 and 20 characters")
    String lastname;

    @Email
    String email;

    Set<Long> roles;
}
