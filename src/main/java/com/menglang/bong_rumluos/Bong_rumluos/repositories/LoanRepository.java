package com.menglang.bong_rumluos.Bong_rumluos.repositories;

import com.menglang.bong_rumluos.Bong_rumluos.entities.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {

    Page<Loan> findAllByCustomer(Long customer_id, Pageable pageable);

    @Query(value = "SELECT l from loans l where l.loan_key = :key Limit 1",nativeQuery = true)
    Loan findLoanByLoanKey(String key);


}
