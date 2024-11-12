package com.menglang.bong_rumluos.Bong_rumluos.entities;

import com.menglang.bong_rumluos.Bong_rumluos.entities.base.BaseAuditEntity;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loans")
public class Loan extends BaseAuditEntity<Long> {

    private BigDecimal totalAmount;
    private Short rate;
    private Short term;
    private BigDecimal principal;
    private BigDecimal amount;
    private Short alert;

    @Enumerated(EnumType.STRING)
    private LoanStatus loanStatus;

    @Column(name = "start_date",nullable = false)
    private LocalDate startDate;
    @Column(name = "end_date",nullable = false)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "customer_id",nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "loan",orphanRemoval = true,cascade = CascadeType.ALL)
    @Builder.Default
    private Set<LoanDetails> loanDetails=new HashSet<>();
}
