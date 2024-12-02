package com.menglang.bong_rumluos.Bong_rumluos.dto.loan;

import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.LoanStatus;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.Terms;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Setter
@Getter
public class LoanDto {
    private String loanKey;
    private BigDecimal principal;
    private double rate;
    private Terms term;
    private LocalDate startDate;
    private LocalDate endDate;
    private int alert;
    private Long customer_id;
    private LoanStatus loanStatus;
}
