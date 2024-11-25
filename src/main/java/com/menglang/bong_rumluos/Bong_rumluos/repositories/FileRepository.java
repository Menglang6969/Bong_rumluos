package com.menglang.bong_rumluos.Bong_rumluos.repositories;

import com.menglang.bong_rumluos.Bong_rumluos.entities.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    @Query("""
            select f from File f
            where upper(f.originalName) like upper(concat('%', ?1, '%')) and f.deletedAt is null""")
    Page<File> findAllByOriginalNameContainsIgnoreCaseAndDeletedAtIsNull(String query,Pageable pageable);



    @Query("""
            select f from File f
            where upper(f.originalName) like upper(concat('%', ?1, '%')) and f.deletedAt is not null""")
    Page<File> findAllByOriginalNameContainsIgnoreCaseAndDeletedAtIsNotNull(String query, Pageable pageable);


}
