package com.menglang.bong_rumluos.Bong_rumluos.services.generateKey.factory;


import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.GenerateKey;
import com.menglang.bong_rumluos.Bong_rumluos.services.generateKey.GenerateNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GenerateKeyFactory {
    private static final Logger log = LoggerFactory.getLogger(GenerateKeyFactory.class);
    private final Map<String, GenerateNumber> mapKeysGenerate;

    public GenerateKeyFactory(List<GenerateNumber> keysGenerate) {
        this.mapKeysGenerate = keysGenerate.stream().collect(Collectors.toMap(key -> key.getClass().getSimpleName(), key -> key));;
    }

    public GenerateNumber getKeyGenerate(GenerateKey type) {
        log.info("Loan type: {} toString {}", type, type.toString());
        log.info("Loan Services: {}", mapKeysGenerate.get(type.toString()));
        GenerateNumber key = mapKeysGenerate.get(type.toString());
        if (key == null) {
            throw new IllegalArgumentException("Invalid Key data: " + type);
        }
        return key;
    }
}
