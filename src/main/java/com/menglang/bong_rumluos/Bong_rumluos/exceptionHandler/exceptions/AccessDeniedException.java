package com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.ResponseTemplate;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class AccessDeniedException implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, org.springframework.security.access.AccessDeniedException accessDeniedException) throws IOException, ServletException {
        var responseStatus = ResponseTemplate.builder()
                .code((short) 401)
                .message("Unauthorized Request")
//                .dateTime(LocalDateTime.now())
                .build();

        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(403);
        response.getWriter().write(mapper.writeValueAsString(
                responseStatus
        ));
    }
}
