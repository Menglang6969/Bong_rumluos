package com.menglang.bong_rumluos.Bong_rumluos.entities;

import com.menglang.bong_rumluos.Bong_rumluos.entities.base.BaseAuditEntity;
import com.menglang.bong_rumluos.Bong_rumluos.entities.enums.IncomeExpenseType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "income_expense")
public class IncomeExpense extends BaseAuditEntity<Long> {

    @Enumerated(EnumType.STRING)
    private IncomeExpenseType type;

    @ManyToOne
    @JoinColumn(name = "income_expense_id")
    private IncomeExpenseCategory category;

    private BigDecimal amount;

    @Column(length = 150)
    private String description;



}
