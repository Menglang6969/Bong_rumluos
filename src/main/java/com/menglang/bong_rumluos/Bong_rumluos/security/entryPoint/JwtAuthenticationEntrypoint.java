package com.menglang.bong_rumluos.Bong_rumluos.security.entryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.menglang.bong_rumluos.Bong_rumluos.helper.ObjectMapperConvertor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntrypoint extends BasicAuthenticationEntryPoint {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationEntrypoint.class);
    private final ObjectMapperConvertor objectMapperConvertor;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {


        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("timestamp", LocalDateTime.now().toString());
        responseBody.put("message", "Unauthorized access");
        responseBody.put("code", 401);

        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(401);
        response.getWriter().write(mapper.writeValueAsString(
                responseBody
        ));

        response.getWriter().flush();
        response.getWriter().close();


    }

    @Override
    public void afterPropertiesSet() {
        // Set the realm name required by BasicAuthenticationEntryPoint
        setRealmName("filterEntryPoint");
        super.afterPropertiesSet();
    }
}
