package com.menglang.bong_rumluos.Bong_rumluos.entities;


import com.menglang.bong_rumluos.Bong_rumluos.entities.base.BaseAuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Table(name = "income_expense_category")
public class IncomeExpenseCategory extends BaseAuditEntity<Long> {

    @Column(length = 100)
    private String title;

    @Column(length = 150)
    private String description;

}
