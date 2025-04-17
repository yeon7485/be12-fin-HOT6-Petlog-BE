package com.hot6.backend.schedule;

import com.hot6.backend.pet.model.Pet;
import com.hot6.backend.schedule.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByUserIdx(Long userIdx);

}
