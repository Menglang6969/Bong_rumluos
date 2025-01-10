package com.menglang.bong_rumluos.Bong_rumluos.security.entryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.ResponseTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JwtAuthenticationEntrypoint extends BasicAuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        var responseStatus = ResponseTemplate.builder()
                .code(HttpStatus.UNAUTHORIZED)
                .message("Unauthorized Request")
                .dateTime(LocalDateTime.now())
                .build();

        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(401);
        response.getWriter().write(mapper.writeValueAsString(
                responseStatus
        ));
    }
    @Override
    public void afterPropertiesSet() {
        // Set the realm name required by BasicAuthenticationEntryPoint
        setRealmName("filterEntryPoint");
        super.afterPropertiesSet();
    }
}
