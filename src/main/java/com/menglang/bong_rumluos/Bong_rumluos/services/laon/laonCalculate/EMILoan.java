package com.menglang.bong_rumluos.Bong_rumluos.services.laon.laonCalculate;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanSchedulerResponse;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import com.menglang.bong_rumluos.Bong_rumluos.utils.BigDecimalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class EMILoan implements LoanCalculateService {

    private static final Logger log = LoggerFactory.getLogger(EMILoan.class);

    @Override
    public List<LoanSchedulerResponse> loanCalculator(BigDecimal principal, double rate, LocalDate startDate, LocalDate endDate) {
        // Calculate loan tenure in months
        int tenureMonths = (int) ChronoUnit.MONTHS.between(startDate, endDate)+1;
        log.info(" total months payments: {}",tenureMonths);
        List<LoanSchedulerResponse> emiSchedule = new ArrayList<>();
        BigDecimal monthlyRate = BigDecimal.valueOf(rate).divide(BigDecimal.valueOf(12 * 100), 10, RoundingMode.HALF_UP);

        BigDecimal emi;
        if (monthlyRate.compareTo(BigDecimal.ZERO) == 0) {
            // Handle zero interest rate: evenly divide principal over months
            emi = principal.divide(BigDecimal.valueOf(tenureMonths), 2, RoundingMode.HALF_UP);
        } else {
            // EMI calculation formula
            emi = principal.multiply(monthlyRate.multiply((BigDecimal.ONE.add(monthlyRate)).pow(tenureMonths)))
                    .divide((BigDecimal.ONE.add(monthlyRate)).pow(tenureMonths).subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
        }

        // EMI calculation formula
//        BigDecimal emi = principal.multiply(monthlyRate.multiply((BigDecimal.ONE.add(monthlyRate)).pow(tenureMonths)))
//                .divide((BigDecimal.ONE.add(monthlyRate)).pow(tenureMonths).subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);


        log.info(" emi: {}",emi);

        BigDecimal remainingPrincipal = principal;

        try {
            for (int month = 0; month <= tenureMonths-1; month++) {
                // Calculate interest and principal components for the current month
                BigDecimal interest = remainingPrincipal.multiply(monthlyRate).setScale(2, RoundingMode.HALF_UP);
                BigDecimal principalPayment = emi.subtract(interest);

                // Create and populate the LoanSchedulerResponse object
                LoanSchedulerResponse emiScheduler = new LoanSchedulerResponse();
                emiScheduler.setRepaymentDate(startDate.plusMonths(month));
                emiScheduler.setPrincipalPayment(emi.setScale(2, RoundingMode.HALF_UP)); // Principal Payment
                emiScheduler.setInterestPayment(interest); // Interest Payment
                emiScheduler.setOutstandingBalance(BigDecimalUtils.convertToZeroIfNegative(remainingPrincipal.subtract(principalPayment).setScale(2, RoundingMode.HALF_UP))); // Remaining Balance
                emiScheduler.setInterestCap(principalPayment.setScale(2, RoundingMode.HALF_UP) ); // EMI amount

                emiSchedule.add(emiScheduler);

                // Update remaining principal
                remainingPrincipal = remainingPrincipal.subtract(principalPayment);

                // Log or print the result
                String result = String.format(
                        "%s | Principal: %s | Interest Capital: %s | INT.PAYMENT: %s | OUTSTANDING BAL: %s",
                        startDate.plusMonths(month),
                        principalPayment,
                        emi,
                        interest,
                        emiScheduler.getOutstandingBalance()
                );
                System.out.println(result);
            }
        } catch (Exception e) {
            throw new BadRequestException("An error occurred while generating the loan schedule: " + e.getMessage());
        }

        return emiSchedule;
    }



}
