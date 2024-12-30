package com.menglang.bong_rumluos.Bong_rumluos.repositories;

import com.menglang.bong_rumluos.Bong_rumluos.entities.IncomeExpenseCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeExpenseCategoryRepository extends JpaRepository<IncomeExpenseCategory, Long> {
    @Query(value = "SELECT * FROM income_expense_category " +
            "WHERE (:query IS NULL OR LOWER(title) LIKE LOWER(CONCAT('%', :query, '%'))) ",
            nativeQuery = true)
    Page<IncomeExpenseCategory> findAllByTitle(@Param("query") String query, Pageable pageable);
}
