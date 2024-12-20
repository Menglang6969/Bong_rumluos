package com.menglang.bong_rumluos.Bong_rumluos.services.invoice.invoiceHelper;

import com.menglang.bong_rumluos.Bong_rumluos.dto.invoice.InvoiceTotalAmountResponse;
import com.menglang.bong_rumluos.Bong_rumluos.entities.LoanRepayment;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@Component
@Lazy
public class InvoiceCalculate {


    public InvoiceTotalAmountResponse calculateTotalAmountLoan(Set<LoanRepayment> loanRepaymentSet) {
        InvoiceTotalAmountResponse invoiceTotalAmountResponse = new InvoiceTotalAmountResponse(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        for (LoanRepayment loanRepayment : loanRepaymentSet) {
            invoiceTotalAmountResponse.setTotalPayment(invoiceTotalAmountResponse.getTotalPayment().add(loanRepayment.getAmountRepay()));
            invoiceTotalAmountResponse.setTotalPenalty(invoiceTotalAmountResponse.getTotalPenalty().add(loanRepayment.getPenalty()));
        }
        invoiceTotalAmountResponse.setTotalAmount(invoiceTotalAmountResponse.getTotalPayment().add(invoiceTotalAmountResponse.getTotalPenalty()));
        return invoiceTotalAmountResponse;
    }

    //Penalty=Principal x dailyPenaltyRate x OverdueDay
    public BigDecimal calculatePenalty(BigDecimal principal, Long rate, LocalDate payDay){
        if (rate!=null && rate>0){
            int overdueDay= (int) ChronoUnit.DAYS.between(payDay,LocalDate.now());
            if(overdueDay>3){
                BigDecimal penaltyRate= BigDecimal.valueOf(rate/100);
                return principal.multiply(penaltyRate).multiply(new BigDecimal(overdueDay-3));
            }
            return  BigDecimal.ZERO;
        }
        return BigDecimal.ZERO;

    }
}
