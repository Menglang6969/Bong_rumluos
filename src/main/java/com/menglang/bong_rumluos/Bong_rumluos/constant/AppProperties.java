package com.menglang.bong_rumluos.Bong_rumluos.constant;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
@Getter
public class AppProperties {

        @Value("${file.app.api-url}")
        private String apiUrl;

        @Value("${file.app.rest-uri}")
        private String restUri;

        @Value("${file.app.table-name}")
        private String tableName;

}
