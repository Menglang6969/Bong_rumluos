package com.menglang.bong_rumluos.Bong_rumluos.repositories;

import com.menglang.bong_rumluos.Bong_rumluos.entities.LoanDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanDetailsRepository extends JpaRepository<LoanDetails, Long> {

    @Query(value = "SELECT ld FROM loan_details ld JOIN FETCH ld.loan l WHERE l.id =:loan_id",nativeQuery = true)
    Page<LoanDetails> findAllByLoan(Long loan_id, Pageable pageable);
}
