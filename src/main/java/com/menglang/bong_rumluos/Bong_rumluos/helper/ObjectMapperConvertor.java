package com.menglang.bong_rumluos.Bong_rumluos.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.ResponseTemplate;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Lazy
@RequiredArgsConstructor
public class ObjectMapperConvertor {
    private static final Logger log = LoggerFactory.getLogger(ObjectMapperConvertor.class);
    private final ObjectMapper objectMapper;

    @Bean
    public void sendErrorResponse(HttpServletResponse response, String errorMessage) throws IOException {
        log.info("error..........");
        var messageException = ResponseTemplate.builder()
                .code((short) 401)
                .message(errorMessage)
                .dateTime(LocalDateTime.now())
                .build();
        var msgJson = objectMapper.writeValueAsString(messageException);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(msgJson);
        response.getWriter().flush();
    }
}
