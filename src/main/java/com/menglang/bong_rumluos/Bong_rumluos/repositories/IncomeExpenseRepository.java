package com.menglang.bong_rumluos.Bong_rumluos.repositories;

import com.menglang.bong_rumluos.Bong_rumluos.entities.IncomeExpense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeExpenseRepository extends JpaRepository<IncomeExpense, Long> {
    @Query(value = "SELECT * FROM income_expense " +
            "WHERE (:query IS NULL OR LOWER(description) LIKE LOWER(CONCAT('%', :query, '%'))) ",
            nativeQuery = true)
    Page<IncomeExpense> findAllDescription(@Param("query") String query, Pageable pageable);
}
