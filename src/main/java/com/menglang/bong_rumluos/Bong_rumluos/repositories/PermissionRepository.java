package com.menglang.bong_rumluos.Bong_rumluos.repositories;

import com.menglang.bong_rumluos.Bong_rumluos.entities.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {

    @Query(value = "SELECT * FROM permissions " +
            "WHERE (:query IS NULL OR LOWER(name) LIKE LOWER(CONCAT('%', :query, '%'))) " ,
            nativeQuery = true)
    Page<Permission> getAllPermission(@Param("query")  String query, Pageable pageable);
}
