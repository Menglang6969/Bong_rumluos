package com.menglang.bong_rumluos.Bong_rumluos.dto.loan;

import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.LoanStatus;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.LoanType;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.Terms;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class LoanDto {
    @NotNull
    @Size(min = 1)
    @Positive(message = "principal must be Positive")
    private BigDecimal principal;

    @NotNull
    @Positive(message = "rate must be positive")
    private double rate;
    @NotNull
    private Terms term;

    @NotNull(message = "start date is null")
    private LocalDate startDate;
    @NotNull(message = "end date is null")
    private LocalDate endDate;
    @NotNull
    @Size(min = 1,max = 7,message = "alert day must be between 1 to 7")
    private int alert;
    @NotNull
    private Long customer_id;
    @NotNull
    private Long product;
    private LoanStatus loanStatus;
    private LoanType type;
    private short penaltyRate;

}
