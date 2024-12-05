package com.menglang.bong_rumluos.Bong_rumluos.utils;

import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.GenerateKey;
import com.menglang.bong_rumluos.Bong_rumluos.services.generateKey.GenerateNumber;
import com.menglang.bong_rumluos.Bong_rumluos.services.generateKey.factory.GenerateKeyFactory;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NumberGenerate {

    private static final Logger log = LoggerFactory.getLogger(NumberGenerate.class);

    private final GenerateKeyFactory generateKeyFactory;
    private static NumberGenerate instance;

    @PostConstruct
    public void init() {
        instance = this; // Set the static instance
    }

    public static String generateKey(GenerateKey type) {
        if (instance == null) {
            throw new IllegalStateException("NumberGenerate is not initialized");
        }
        GenerateNumber generateKey = instance.generateKeyFactory.getKeyGenerate(type);
        String key = generateKey.generateKey();
        log.info("Key generated: {}", key);
        return key;
    }
}