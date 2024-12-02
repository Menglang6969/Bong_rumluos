package com.menglang.bong_rumluos.Bong_rumluos.dto.loan.loanDetails;

import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.LoanResponse;
import com.menglang.bong_rumluos.Bong_rumluos.dto.pageResponse.StatusResponse;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.LoanStatus;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoanDetailsResponse {
    private LocalDate repaymentDate;
    private Double totalRepayment;
    private Double principal; //base amount
    private Double interestPayment;
    private Double outstandingBalance;
    private LoanStatus status;
}
