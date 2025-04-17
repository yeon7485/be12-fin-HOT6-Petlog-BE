package com.hot6.backend.schedule;

import com.hot6.backend.category.model.Category;
import com.hot6.backend.chat.model.ChatDto;
import com.hot6.backend.common.BaseResponseStatus;
import com.hot6.backend.common.exception.BaseException;
import com.hot6.backend.schedule.model.Schedule;
import com.hot6.backend.schedule.model.ScheduleDto;
import com.hot6.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    public void createSchedule(User user, Long petIdx, ScheduleDto.ScheduleCreateRequest dto) {
        scheduleRepository.save(dto.toEntity(user, petIdx));
    }

    public List<ScheduleDto.SimpleSchedule> getAllSchedule(Long userIdx) {
        List<Schedule> schedules = scheduleRepository.findAllByUserIdx(userIdx);

        List<ScheduleDto.SimpleSchedule> scheduleList = new ArrayList<>();

        for(Schedule schedule : schedules) {
            // [TODO]: 카테고리 연동 후 수정
            Category category = Category.builder()
                    .name("병원")
                    .color("#00C9CD")
                    .idx(schedule.getCategoryIdx())
                    .build();
            scheduleList.add(ScheduleDto.SimpleSchedule.from(schedule, category));
        }

        return scheduleList;


    }
}
