package com.hot6.backend.notification;

import com.hot6.backend.notification.model.NotificationDto;
import com.hot6.backend.schedule.ScheduleRepository;
import com.hot6.backend.schedule.model.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ScheduleNotificationScheduler {
    private final ScheduleRepository scheduleRepository;
    private final NotificationService notificationService;

    @Scheduled(fixedRate = 60000) // 매 분마다 실행
    public void notifyOneHourBefore() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime targetTime = now.plusHours(1).withSecond(0).withNano(0);

        // ±1분 허용해서 유연하게 탐색
        LocalDateTime start = targetTime.minusMinutes(1);
        LocalDateTime end = targetTime.plusMinutes(1);

        List<Schedule> schedules = scheduleRepository.findByStartAtBetween(start, end);

        for (Schedule schedule : schedules) {
            Long userIdx = schedule.getPet().getUser().getIdx();
            if (userIdx == null) {
                System.out.println("❗ 사용자 정보가 없는 일정: " + schedule.getIdx());
                continue;
            }

            NotificationDto.NotificationSendRequest request = NotificationDto.NotificationSendRequest.builder()
                    .userId(userIdx)
                    .scheduleId(schedule.getIdx())
                    .message("[알림] \"" + schedule.getSTitle() + "\" 일정이 1시간 남았습니다.")
                    .build();

            notificationService.createNotification(request, userIdx);
        }
    }
}
