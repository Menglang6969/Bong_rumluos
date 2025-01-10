package com.menglang.bong_rumluos.Bong_rumluos.controllers.auth;

import com.menglang.bong_rumluos.Bong_rumluos.dto.ResponseTemplate;
import com.menglang.bong_rumluos.Bong_rumluos.dto.authentication.*;
import com.menglang.bong_rumluos.Bong_rumluos.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ResponseTemplate> register(@RequestBody RegisterDTO dto) {
        return ResponseEntity.ok(authenticationService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseTemplate> login(@RequestBody AuthenticationReq req){
        return ResponseEntity.ok(authenticationService.authenticate(req));
    }

    @PutMapping("/reset-password")
    public ResponseEntity<ResponseTemplate> changePassword(@RequestBody ResetPasswordDTO dto){
        return ResponseEntity.ok(authenticationService.resetPassword(dto));
    }
}
