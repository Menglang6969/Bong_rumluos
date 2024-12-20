package com.menglang.bong_rumluos.Bong_rumluos.dto.loan;

import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.LoanType;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.Terms;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanRequestBaseDto {

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
    private short alert;
    @NotNull
    private Long customer_id;
    @NotNull
    private List<Long> products;

    private LoanType type;
    private BigDecimal deposit;
    private String description;
}
