package com.menglang.bong_rumluos.Bong_rumluos.dto.loan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.LoanType;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.Terms;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter

public class LoanRestructureDto extends LoanDto {

    private Long loanId;

    public LoanRestructureDto(BigDecimal principal, double rate, Terms term, LocalDate startDate, LocalDate endDate, short alert, Long customer_id, List<Long> products, LoanType type, BigDecimal deposit, String description, Long loanId) {
        super(principal, rate, term, startDate, endDate, alert, customer_id, products, type, deposit, description);
        this.loanId = loanId;
    }

//    public LoanRestructureDto(@NotNull @Size(min = 1) @Positive(message = "principal must be Positive") BigDecimal principal, @NotNull @Positive(message = "rate must be positive") double rate, @NotNull Terms term, @NotNull(message = "start date is null") LocalDate startDate, @NotNull(message = "end date is null") LocalDate endDate, @NotNull @Size(min = 1, max = 7, message = "alert day must be between 1 to 7") short alert, @NotNull Long customer_id, @NotNull List<Long> products, LoanType type, BigDecimal deposit, String description) {
//        super(principal, rate, term, startDate, endDate, alert, customer_id, products, type, deposit, description);
//    }


    @JsonIgnore // This will exclude the field from JSON serialization and deserialization
    @Override
    public BigDecimal getPrincipal() {
        return null;
    }

    @Override
    public void setPrincipal(BigDecimal principal) {
        // Do nothing to effectively disable this field
    }


}
