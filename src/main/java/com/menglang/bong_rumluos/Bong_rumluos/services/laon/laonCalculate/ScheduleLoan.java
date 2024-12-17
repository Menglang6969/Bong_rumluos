package com.menglang.bong_rumluos.Bong_rumluos.services.laon.laonCalculate;

import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanSchedulerResponse;
import com.menglang.bong_rumluos.Bong_rumluos.utils.CheckWeekend;
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
public class ScheduleLoan implements LoanCalculateService {

    private static final Logger log = LoggerFactory.getLogger(ScheduleLoan.class);

    @Override
    public List<LoanSchedulerResponse> loanCalculator(BigDecimal principalAmount, double annualRate,
                                                      LocalDate startDate, LocalDate endDate) {

        List<LoanSchedulerResponse> schedule = new ArrayList<>();
        log.info(" principal :{}",principalAmount);
        log.info(" rate :{}",annualRate);
        log.info(" start date :{}",startDate);
        log.info(" end date :{}",endDate);

        // Calculate total months
        int totalMonths = (int) ChronoUnit.MONTHS.between(startDate, endDate) + 1;
        BigDecimal periods= BigDecimal.valueOf((double) totalMonths /12);
        BigDecimal principal = principalAmount.divide(BigDecimal.valueOf(totalMonths), 10, RoundingMode.HALF_UP);

        BigDecimal totalInterest = principalAmount.multiply(BigDecimal.valueOf(annualRate / 100)).multiply(periods);


        BigDecimal interestCap = totalInterest.divide(BigDecimal.valueOf(totalMonths), 2, RoundingMode.HALF_UP);
        BigDecimal monthlyPrincipal = principal.add(interestCap);

        BigDecimal outstandingBalance = principalAmount.add(totalInterest).setScale(2,RoundingMode.HALF_UP);


        for (int month = 0; month < totalMonths ; month++) {
            outstandingBalance = outstandingBalance.subtract(monthlyPrincipal);
            if (outstandingBalance.compareTo(BigDecimal.ZERO) <= 0 ) {
                outstandingBalance = BigDecimal.ZERO;
            }

            LoanSchedulerResponse schedulerPay = new LoanSchedulerResponse();
            schedulerPay.setInterestCap(principal);
            schedulerPay.setInterestPayment(interestCap);
            schedulerPay.setOutstandingBalance(outstandingBalance);
            schedulerPay.setRepaymentDate(CheckWeekend.validateWeekend(startDate.plusMonths(month)));
            schedulerPay.setPrincipalPayment(monthlyPrincipal);
            schedule.add(schedulerPay);
        }

        return schedule;

    }
}
