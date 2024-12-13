package com.menglang.bong_rumluos.Bong_rumluos.utils;

import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.GenerateKey;
import com.menglang.bong_rumluos.Bong_rumluos.services.generateKey.GenerateNumber;
import com.menglang.bong_rumluos.Bong_rumluos.services.generateKey.factory.GenerateKeyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Lazy
public class SequenceKeyGenerator {
    private final GenerateKeyFactory generateKeyFactory;

    public String generateLoanKey() {
        GenerateNumber generateKey = generateKeyFactory.getKeyGenerate(GenerateKey.LoanKey);
        return generateKey.generateKey();
    }

    public String generateInvoiceKey() {
        GenerateNumber generateKey = generateKeyFactory.getKeyGenerate(GenerateKey.InvoiceKey);
        return generateKey.generateKey();
    }
}
