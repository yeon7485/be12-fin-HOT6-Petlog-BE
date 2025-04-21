package com.hot6.backend.schedule;

import com.hot6.backend.category.model.Category;
import com.hot6.backend.category.model.CategoryRepository;
import com.hot6.backend.chat.model.ChatDto;
import com.hot6.backend.chat.model.ChatRoom;
import com.hot6.backend.common.BaseResponseStatus;
import com.hot6.backend.common.exception.BaseException;
import com.hot6.backend.pet.PetRepository;
import com.hot6.backend.pet.model.Pet;
import com.hot6.backend.schedule.model.Schedule;
import com.hot6.backend.schedule.model.ScheduleDto;
import com.hot6.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CategoryRepository categoryRepository;
    private final PetRepository petRepository;

    public void createSchedule(User user, Long petIdx, ScheduleDto.ScheduleCreateRequest dto) {
        Category category = categoryRepository.findById(dto.getCategoryIdx())
                .orElseThrow(() -> new IllegalArgumentException("카테고리가 존재하지 않습니다."));

        Pet pet = petRepository.findById(petIdx)
                .orElseThrow(() -> new IllegalArgumentException("해당 반려동물이 존재하지 않습니다."));

        Schedule schedule = dto.toEntity(user, category, pet);
        scheduleRepository.save(schedule);
    }
    public List<ScheduleDto.SimpleSchedule> getAllSchedule(Long userIdx) {
        List<Schedule> schedules = scheduleRepository.findAllByUserIdx(userIdx);
        List<ScheduleDto.SimpleSchedule> result = new ArrayList<>();

        for (Schedule s : schedules) {
            Category category = s.getCategory();
            if (category == null) continue; // 방어적 처리
            result.add(ScheduleDto.SimpleSchedule.from(s, category));
        }

        return result;
    }

    public Schedule getSchedule(Long scheduleIdx) {
        return scheduleRepository.findById(scheduleIdx).orElseThrow(() -> new BaseException(BaseResponseStatus.SCHEDULE_NOT_FOUND));
    }

    public List<ChatDto.ChatRoomScheduleElement> getALLScheduleByChatRoom(Long chatRoomIdx) {
        return scheduleRepository.findAllWithChatRoomByChatRoomIdx(chatRoomIdx).stream().map(ChatDto.ChatRoomScheduleElement::from).collect(toList());
    }

    public void createChatRoomSchedule(ChatDto.CreateChatRoomScheduleRequest dto, ChatRoom chatRoom, User user) {
        Category category = categoryRepository.findById(dto.getCategoryIdx())
                .orElseThrow(() -> new IllegalArgumentException("카테고리가 존재하지 않습니다."));
        scheduleRepository.save(dto.toEntity(user,chatRoom,category));
    }

    public List<User> findChatRoomUsersParticipatingInSchedule(Long scheduleIdx) {
        return scheduleRepository.findChatRoomUsersParticipatingInSchedule(scheduleIdx);
    }
}
