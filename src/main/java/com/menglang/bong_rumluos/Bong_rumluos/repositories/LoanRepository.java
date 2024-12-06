package com.menglang.bong_rumluos.Bong_rumluos.repositories;

import com.menglang.bong_rumluos.Bong_rumluos.entities.Customer;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    Page<Loan> findAllByCustomer(Long customer_id, Pageable pageable);

    @Query(value = "SELECT l from loans l where l.loan_key = :key Limit 1", nativeQuery = true)
    Loan findLoanByLoanKey(String key);

    //    @Query(value = "SELECT l from loans l INNER JOINT customers c on l.customer_id=c.id where l.customer_id=:cid", nativeQuery = true)
//    List<Loan> findByCustomerId(Long cid);
    @Query("SELECT l FROM Loan l WHERE l.customer = :customer")
    List<Loan> findByCustomerId(@Param("customer") Customer customer);

    @Query("SELECT l FROM Loan l " +
            "JOIN l.customer c " +
            "WHERE (:query IS NULL OR :query = '' " +
            "OR LOWER(l.loanKey) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(c.phone) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<Loan> findByLoanKeyOrCustomerNameOrPhone(@Param("query") String query, Pageable pageable);




}
