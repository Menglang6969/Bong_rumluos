package com.menglang.bong_rumluos.Bong_rumluos.services.generateKey;

import com.menglang.bong_rumluos.Bong_rumluos.entities.SequenceNumber;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.NotFoundException;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.SequenceNumberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class InvoiceKey implements GenerateNumber{
    private final SequenceNumberRepository sequenceNumberRepository;
    @Override
    public String generateKey() {
        SequenceNumber sequenceNumber = sequenceNumberRepository.findById(1L).orElseThrow(() -> new NotFoundException("Not Found Sequence Number"));
        BigDecimal lastNumber = sequenceNumber.getInvoiceNumber();

        BigDecimal newKey = lastNumber.add(BigDecimal.ONE);
        String formattedNumber = String.format("%07d",newKey.longValue());
        try{
            sequenceNumber.setInvoiceNumber(newKey);
            sequenceNumberRepository.save(sequenceNumber);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
        return "INV" + formattedNumber;
    }
}
