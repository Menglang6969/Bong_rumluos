package com.menglang.bong_rumluos.Bong_rumluos.repositories;

import com.menglang.bong_rumluos.Bong_rumluos.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Category c WHERE c.name = ?1 AND c.id != ?2")
    public boolean existsByNameAndIdNot(String name, Long id);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END from Category c Where c.name=?1")
    public boolean existsByName(String name);

    @Query(value = "SELECT * FROM categories " +
            "WHERE (:query IS NULL OR LOWER(name) LIKE LOWER(CONCAT('%', :query, '%'))) " ,
            nativeQuery = true)
    Page<Category> findAllByName(@Param("query") String query, Pageable pageable);
}
