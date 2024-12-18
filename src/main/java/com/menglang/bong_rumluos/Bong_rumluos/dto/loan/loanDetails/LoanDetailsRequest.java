package com.menglang.bong_rumluos.Bong_rumluos.dto.loan.loanDetails;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.LoanStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoanDetailsRequest {
    private LocalDate repaymentDate;
    private BigDecimal principal; //base amount
    private BigDecimal interestPayment;
    private BigDecimal interestCap;
    private BigDecimal outstandingBalance;
    private Long loan;
    private Boolean isPenalty;
    private LoanStatus status;

}
