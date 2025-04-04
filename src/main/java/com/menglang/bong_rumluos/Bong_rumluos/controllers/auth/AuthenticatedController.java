package com.menglang.bong_rumluos.Bong_rumluos.controllers.auth;

import com.menglang.bong_rumluos.Bong_rumluos.dto.ResponseTemplate;
import com.menglang.bong_rumluos.Bong_rumluos.dto.authentication.ResetPasswordDTO;
import com.menglang.bong_rumluos.Bong_rumluos.security.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AuthenticatedController {
    private final AuthenticationService authenticationService;

    public AuthenticatedController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PutMapping("/reset-password")
    public ResponseEntity<ResponseTemplate> changePassword(@RequestBody ResetPasswordDTO dto){
        return ResponseEntity.ok(authenticationService.resetPassword(dto));
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseTemplate> me(Authentication authentication){
        return ResponseEntity.ok( authenticationService.getMe(authentication));
    }
}
