package com.hot6.backend.record;

import com.hot6.backend.pet.model.Pet;
import com.hot6.backend.record.model.DailyRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DailyRecordRepository extends JpaRepository<DailyRecord, Long> {
    List<DailyRecord> findAllByPetAndDateBetween(Pet pet, LocalDateTime start, LocalDateTime end);
}

