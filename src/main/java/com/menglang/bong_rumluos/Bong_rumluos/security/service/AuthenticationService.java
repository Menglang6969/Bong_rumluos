package com.menglang.bong_rumluos.Bong_rumluos.security.service;

import com.menglang.bong_rumluos.Bong_rumluos.dto.ResponseTemplate;
import com.menglang.bong_rumluos.Bong_rumluos.dto.authentication.*;

public interface AuthenticationService {

    public ResponseTemplate register(RegisterDTO RegisterDto);
    public ResponseTemplate authenticate(AuthenticationReq loginDto);
    public ResponseTemplate resetPassword(ResetPasswordDTO resetPasswordDTO);


}
