package com.menglang.bong_rumluos.Bong_rumluos.services.laon.laonCalculate;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanSchedulerResponse;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduleLoan implements LoanCalculateService {

    @Override
    public List<LoanSchedulerResponse> loanCalculator(BigDecimal principalAmount, double annualInterestRate,
                                                      LocalDate startDate, LocalDate endDate) {
        long termInMonths = ChronoUnit.MONTHS.between(startDate, endDate);
        List<LoanSchedulerResponse> repaymentSchedule = new ArrayList<>();
        double monthlyInterestRate = annualInterestRate / 12 / 100;
        BigDecimal outstandingBalance = principalAmount;
        BigDecimal monthlyPrincipalPayment = principalAmount.divide(BigDecimal.valueOf(termInMonths), new MathContext(2));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

        // Loop to calculate each month's repayment data
        for (int i = 0; i < termInMonths; i++) {
            //Calculate interest for the current month
            BigDecimal interestForMonth = outstandingBalance.multiply(BigDecimal.valueOf(monthlyInterestRate)).setScale(2, RoundingMode.HALF_UP);

            // Calculate total monthly payment (Principal + Interest)
            BigDecimal totalMonthlyPayment = monthlyPrincipalPayment.add(interestForMonth).setScale(2, RoundingMode.HALF_UP);

            outstandingBalance = outstandingBalance.subtract(monthlyPrincipalPayment).setScale(2, RoundingMode.HALF_UP);
            // Add repayment schedule for the current month
            repaymentSchedule.add(new LoanSchedulerResponse(
                    (long) i,
                    startDate.plusMonths(i),
                    monthlyPrincipalPayment,
                    interestForMonth,
                    totalMonthlyPayment,
                    outstandingBalance
            ));
//
//            String result = String.format(
//                    "%s | Principal: %s | Interest Capital: %s | INT.PAYMENT: %s | OUTSTANDING BAL: %s",
//                    startDate.plusMonths(month),
//                    principalPayment,
//                    emi,
//                    interest,
//                    emiScheduler.getOutstandingBalance()
//            );
        }

        return repaymentSchedule;
    }
}
