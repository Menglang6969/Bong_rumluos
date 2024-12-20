package com.menglang.bong_rumluos.Bong_rumluos.services.laon.laonCalculate;

import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.LoanType;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Lazy
public class LoanFactory {
    private static final Logger log = LoggerFactory.getLogger(LoanFactory.class);
    private final Map<String, LoanCalculateService> mapLoanServices;

    public LoanFactory(List<LoanCalculateService> loanServices) {
        this.mapLoanServices = loanServices.stream().collect(Collectors.toMap(loan -> loan.getClass().getSimpleName(), loan -> loan));
    }

    public LoanCalculateService getPaymentService(LoanType type) {
        try{
            LoanCalculateService service = mapLoanServices.get(type.toString());
            if (service == null) {
                throw new IllegalArgumentException("Invalid payment type: " + type);
            }
            return service;
        }catch (Exception e){
            log.warn("Loan Factory Error: {}",e.getLocalizedMessage());
            throw new BadRequestException(e.getMessage());
        }

    }
}
