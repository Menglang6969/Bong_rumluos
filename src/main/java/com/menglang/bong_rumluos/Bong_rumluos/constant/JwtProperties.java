package com.menglang.bong_rumluos.Bong_rumluos.constant;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class JwtProperties {

    @Value("${jwt.url}")
    private String url;

    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.prefix}")
    private String prefix;

    @Value("${jwt.expire}")
    private String expire;

    @Value("${jwt.refresh_expire}")
    private String refresh_token_expire;

    @Value("${jwt.secrete}")
    private String secrete;
}
