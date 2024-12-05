package com.menglang.bong_rumluos.Bong_rumluos.services.laon.laonCalculate;

import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanSchedulerResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface LoanCalculateService {
    /**
     * Calculate loan repayment schedule dynamically with standardized response data.
     *
     * @param principal      The loan principal amount
     * @param annualRate     The annual interest rate (in percentage, e.g., 16 for 16%)
     * @param startDate      The loan start date
     * @param endDate        The loan end date
     * @return List of standardized repayment schedules
     */
    public List<LoanSchedulerResponse> loanCalculator(BigDecimal principal, double rate, LocalDate startDate, LocalDate endDate);
}
