package com.hot6.backend.schedule;

import com.hot6.backend.pet.model.Pet;
import com.hot6.backend.schedule.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByUserIdx(Long userIdx);

    @Query("SELECT s FROM Schedule s JOIN FETCH s.chatRoom WHERE s.chatRoom.idx = :chatRoomIdx")
    List<Schedule> findAllWithChatRoomByChatRoomIdx(Long chatRoomIdx);

    @Query("""
    SELECT s FROM Schedule s
    JOIN FETCH s.pet p
    JOIN FETCH p.user u
    WHERE s.idx = :scheduleIdx
""")
    Optional<Schedule> findByIdWithPetAndUser(Long scheduleIdx);
}
