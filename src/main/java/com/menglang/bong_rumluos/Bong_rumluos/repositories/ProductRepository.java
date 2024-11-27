package com.menglang.bong_rumluos.Bong_rumluos.repositories;

import com.menglang.bong_rumluos.Bong_rumluos.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(value = "SELECT * FROM products " +
            "WHERE ((:query IS NULL OR LOWER(name) LIKE LOWER(CONCAT('%', :query, '%'))) " +
            "OR (:query IS NULL OR LOWER(identify1) LIKE LOWER(CONCAT('%', :query, '%'))) " +
            "OR (:query IS NULL OR LOWER(identify2) LIKE LOWER(CONCAT('%', :query, '%')))) " +
            "AND (:category_id IS NULL OR category_id = :category_id) " +
            "AND DeletedAt IS NULL",
            nativeQuery = true)
    Page<Product> findAllByNameOrCodeAndDeletedIsNull(String query,Long category_id, Pageable pageable);


    @Query(value = "SELECT * FROM products " +
            "WHERE ((:query IS NULL OR LOWER(name) LIKE LOWER(CONCAT('%', :query, '%'))) " +
            "OR (:query IS NULL OR LOWER(identify1) LIKE LOWER(CONCAT('%', :query, '%'))) " +
            "OR (:query IS NULL OR LOWER(identify2) LIKE LOWER(CONCAT('%', :query, '%')))) " +
            "AND (:category_id IS NULL OR category_id = :category_id) ",
            nativeQuery = true)
    Page<Product> findAllByNameOrCodeAndDeletedIsNotNull(String query, Long category_id, Pageable pageable);


}
