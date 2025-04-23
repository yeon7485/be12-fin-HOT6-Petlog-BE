package com.hot6.backend.notification;

import com.hot6.backend.notification.model.Notification;
import com.hot6.backend.notification.model.NotificationDto;
import com.hot6.backend.schedule.ScheduleRepository;
import com.hot6.backend.schedule.model.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final ScheduleRepository scheduleRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public void createNotification(NotificationDto.NotificationSendRequest request) {
        // 1. 스케줄 조회
        Schedule schedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄 없음"));

        // 2. DTO → 엔티티로 변환
        Notification notification = NotificationDto.NotificationElement.builder()
                .message(request.getMessage())
                .sentAt(LocalDateTime.now())
                .build()
                .toEntity(schedule);

        // 3. 저장
        notificationRepository.save(notification);

        // 4. 웹소켓 전송
        NotificationDto.NotificationElement element = NotificationDto.NotificationElement.builder()
                .message(notification.getMessage())
                .sentAt(notification.getSentAt())
                .build();

        messagingTemplate.convertAndSend("/topic/alerts/" + schedule.getUser().getIdx(), element);
    }

    public List<NotificationDto.NotificationElement> getNotificationsByUserId(Long userIdx) {
        return notificationRepository.findAllByScheduleUserIdxOrderBySentAtDesc(userIdx).stream()
                .map(notification -> NotificationDto.NotificationElement.builder()
                        .message(notification.getMessage())
                        .sentAt(notification.getSentAt())
                        .build())
                .toList();
    }

    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("알림이 존재하지 않습니다."));

        notification.setRead(true); // ✅ 읽음 처리
        notificationRepository.save(notification);
    }
}
