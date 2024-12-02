package com.menglang.bong_rumluos.Bong_rumluos.entities;

import com.menglang.bong_rumluos.Bong_rumluos.entities.base.BaseAuditEntity;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "loan_details")
public class LoanDetails extends BaseAuditEntity<Long> {

    @Column(name = "repayment_date")
    private LocalDate repaymentDate;

    @Column(name = "total_repayment",nullable = false)//amount to pay every month
    private BigDecimal totalRepayment;

    @Column(nullable  = false)
    private BigDecimal principal; //base amount

    @Column(nullable = false,name = "interest_payment")
    private BigDecimal interestPayment;

    @Column(name = "outstanding_balance")//rest of money
    private BigDecimal outstandingBalance;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

}
