package com.menglang.bong_rumluos.Bong_rumluos.services.loanDetails;

import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanResponse;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.loanDetails.LoanDetailsResponse;

public interface LoanDetailsService {
    LoanResponse updateLoanDetailsStatus(Long id);
}