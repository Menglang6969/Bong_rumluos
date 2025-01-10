package com.menglang.bong_rumluos.Bong_rumluos.dto.authentication;

public record ResetPasswordDTO(
        String username,
        String password,
        String newPassword
) {
}
