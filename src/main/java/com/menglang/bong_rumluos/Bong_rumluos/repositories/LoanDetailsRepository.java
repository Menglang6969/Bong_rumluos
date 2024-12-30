package com.menglang.bong_rumluos.Bong_rumluos.repositories;

import com.menglang.bong_rumluos.Bong_rumluos.entities.LoanDetails;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface LoanDetailsRepository extends JpaRepository<LoanDetails, Long> {

    @Query(value = "SELECT ld FROM loan_details ld JOIN FETCH ld.loan l WHERE l.id =:loan_id ",nativeQuery = true)
    Page<LoanDetails> findAllByLoan(Long loan_id, Pageable pageable);

    @Query(value = "SELECT ld FROM loan_details ld " +
            "JOIN FETCH ld.loan l " +
            "LEFT JOIN loan_repayment lr ON lr.loan_details_id = ld.id " +
            "WHERE l.id = :loan_id " +
            "ORDER BY lr.repayment_date ASC",
            nativeQuery = true)
    Page<LoanDetails> findAllByLoanOrderByRepaymentDateAsc(@Param("loan_id") Long loanId, Pageable pageable);


    @Query(value = "SELECT ld.* FROM loan_details ld INNER JOIN loans l ON l.id = ld.loan_id WHERE l.id = :loan_id AND ld.status = 'WAITING'", nativeQuery = true)
    List<LoanDetails> findAllByLoanId(@Param("loan_id") Long loanId);

    //stream data
    @QueryHints(value = @QueryHint(name = "org.hibernate.fetchSize",value = "5"))// fetch 5 records a time until out of rows
    @Query("SELECT ld FROM LoanDetails ld where ld.status=WAITING")
    Stream<LoanDetails> getLoanDetailsUpComingPayment();

    @Query("SELECT ld FROM LoanDetails ld where ld.status=WAITING")
    List<LoanDetails> getLoanDetailsUpComingPayment(Pageable pageable);


}
