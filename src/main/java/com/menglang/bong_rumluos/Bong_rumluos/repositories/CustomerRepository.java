package com.menglang.bong_rumluos.Bong_rumluos.repositories;

import com.menglang.bong_rumluos.Bong_rumluos.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT * FROM customers " +
            "WHERE (:query IS NULL OR LOWER(name) LIKE LOWER(CONCAT('%', :query, '%'))) " +
            "OR (:query IS NULL OR LOWER(phone) LIKE LOWER(CONCAT('%', :query, '%'))) " ,
            nativeQuery = true)
    Page<Customer> findAllByNameOrPhone(@Param("query") String query, Pageable pageable);
}
