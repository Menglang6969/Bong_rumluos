package com.menglang.bong_rumluos.Bong_rumluos.services.loanDetails;

import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanResponse;

import java.math.BigDecimal;

public interface LoanDetailsService {
    LoanResponse updateLoanDetailsStatus(Long id);
    void updateLoanDetailsStatusAndPenalty(Long id, Long penalty);
    BigDecimal getOutStandingBalanceCurrentLoan(Long id);

}
