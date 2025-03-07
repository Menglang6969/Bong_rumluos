package com.menglang.bong_rumluos.Bong_rumluos.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.menglang.bong_rumluos.Bong_rumluos.dto.ResponseTemplate;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@Lazy
@RequiredArgsConstructor
public class ObjectMapperConvertor {
    private static final Logger log = LoggerFactory.getLogger(ObjectMapperConvertor.class);

    @Bean
    public void sendErrorResponse(HttpServletResponse response, String errorMessage) throws IOException {


        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("timestamp", LocalDateTime.now().toString());
        responseBody.put("message", errorMessage);
        responseBody.put("code", 401);

        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        var msgJson = mapper.writeValueAsString(responseBody);
        log.info("Msg Json: {}", msgJson);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(msgJson);
        response.getWriter().flush();
    }
}
