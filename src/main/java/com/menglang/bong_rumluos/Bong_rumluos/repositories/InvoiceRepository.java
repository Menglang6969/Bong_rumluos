package com.menglang.bong_rumluos.Bong_rumluos.repositories;

import com.menglang.bong_rumluos.Bong_rumluos.entities.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {

    @Query(value = "SELECT i FROM invoices INNER JOINT customers c On i.customer_id=c.id" +
            "WHERE (:query IS NULL OR LOWER(c.invoice_no) LIKE LOWER(CONCAT('%', :query, '%'))) " +
            "OR (:query IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%'))) " ,
            nativeQuery = true)
    Page<Invoice> getAllInvoiceByInvoiceNoOrCustomerName(@Param("query") String query, Pageable pageable);
}
