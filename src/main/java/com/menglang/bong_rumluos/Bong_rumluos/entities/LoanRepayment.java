package com.menglang.bong_rumluos.Bong_rumluos.entities;


import com.menglang.bong_rumluos.Bong_rumluos.entities.base.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
@Table(name = "loan_repayments")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanRepayment extends BaseAuditEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "loan_details_id",nullable = false)
    private LoanDetails loanDetails;

    @Column(name = "repayment_date")
    private LocalDate repaymentDate;

    @Column(name = "amount_repay")
    private BigDecimal amountRepay;

    @Column(length = 100)
    private String description;

    private BigDecimal penalty;

    @Transient
    private BigDecimal totalPayment;

}
