package com.hot6.backend.record;

import com.hot6.backend.record.model.DailyRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyRecordRepository extends JpaRepository<DailyRecord, Long> {

}
