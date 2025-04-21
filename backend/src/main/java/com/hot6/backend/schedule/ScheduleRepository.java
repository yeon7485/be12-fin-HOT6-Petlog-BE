package com.hot6.backend.schedule;

import com.hot6.backend.pet.model.Pet;
import com.hot6.backend.schedule.model.Schedule;
import com.hot6.backend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByPet(Pet pet);

    @Query("SELECT s FROM Schedule s JOIN FETCH s.chatRoom WHERE s.chatRoom.idx = :chatRoomIdx")
    List<Schedule> findAllWithChatRoomByChatRoomIdx(Long chatRoomIdx);

//    @Query("""
//    SELECT DISTINCT u FROM Schedule s
//    JOIN s.sharedSchedules sp
//    JOIN sp.pet p
//    JOIN p.user u
//    JOIN s.chatRoom cr
//    JOIN cr.participants cp
//    WHERE s.idx = :scheduleIdx
//      AND cp.user = u
//""")
@Query("""
    SELECT DISTINCT u FROM Schedule s
    JOIN s.sharedSchedules sp
    JOIN sp.pet p
    JOIN p.user u
    JOIN s.chatRoom cr
    JOIN cr.participants cp
    WHERE s.idx = :scheduleIdx
      AND cp.user.idx = u.idx
""")
    List<User> findChatRoomUsersParticipatingInSchedule(Long scheduleIdx);
}
