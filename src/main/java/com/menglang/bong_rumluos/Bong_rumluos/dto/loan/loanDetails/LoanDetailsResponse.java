package com.menglang.bong_rumluos.Bong_rumluos.dto.loan.loanDetails;

import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.LoanStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoanDetailsResponse {
    Long id;
    private LocalDate repaymentDate;
    private BigDecimal principal; //base amount
    private BigDecimal interestCap;
    private BigDecimal interestPayment;
    private BigDecimal outstandingBalance;
    private Boolean isPenalty;
    private LoanStatus status;
}
