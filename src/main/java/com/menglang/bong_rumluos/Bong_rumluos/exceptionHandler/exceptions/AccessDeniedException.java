package com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions;

import com.menglang.bong_rumluos.Bong_rumluos.helper.ObjectMapperConvertor;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccessDeniedException implements AccessDeniedHandler {
    private final ObjectMapperConvertor objectMapperConvertor;

    public AccessDeniedException(ObjectMapperConvertor objectMapperConvertor) {
        this.objectMapperConvertor = objectMapperConvertor;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, org.springframework.security.access.AccessDeniedException accessDeniedException) throws IOException, ServletException {
        var message = "You don't have Permission to Access this Resource.";
        objectMapperConvertor.sendErrorResponse(response, message, (short) HttpServletResponse.SC_FORBIDDEN);

    }
}
