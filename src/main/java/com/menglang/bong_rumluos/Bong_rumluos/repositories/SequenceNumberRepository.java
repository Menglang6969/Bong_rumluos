package com.menglang.bong_rumluos.Bong_rumluos.repositories;

import com.menglang.bong_rumluos.Bong_rumluos.entities.SequenceNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceNumberRepository extends JpaRepository<SequenceNumber,Long> {
}
