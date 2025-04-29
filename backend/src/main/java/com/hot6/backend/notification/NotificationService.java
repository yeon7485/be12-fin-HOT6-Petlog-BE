package com.hot6.backend.notification;

import com.hot6.backend.common.BaseResponseStatus;
import com.hot6.backend.common.exception.BaseException;
import com.hot6.backend.notification.model.Notification;
import com.hot6.backend.notification.model.NotificationDto;
import com.hot6.backend.pet.PetRepository;
import com.hot6.backend.pet.model.Pet;
import com.hot6.backend.schedule.ScheduleRepository;
import com.hot6.backend.schedule.model.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final ScheduleRepository scheduleRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final PetRepository petRepository;

    @Transactional(readOnly = false)
    public void createNotification(NotificationDto.NotificationSendRequest request, Long userIdx) {
        // 1. 스케줄 조회
        Schedule schedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOTIFICATION_NOT_FOUND));

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

        messagingTemplate.convertAndSend("/topic/alerts/" + userIdx, element);
    }
    @Transactional(readOnly = true)
    public List<NotificationDto.NotificationElement> getNotificationsByUserId(Long userIdx) {
        List<NotificationDto.NotificationElement> list = new ArrayList<>();

        List<Pet> petList = petRepository.findByUserIdx(userIdx);

        for (Pet pet : petList) {

            List<Notification> notiList =  notificationRepository.findAllBySchedulePetOrderBySentAtDesc(pet);
            list.addAll(notiList.stream()
                    .map(NotificationDto.NotificationElement::from)
                    .toList());
        }

        return list;
    }

    @Transactional
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOTIFICATION_NOT_FOUND));

        notification.setRead(true); // ✅ 읽음 처리
        notificationRepository.save(notification);
    }

    @Transactional(readOnly = false)
    public void deleteById(Long idx) {
        if (!notificationRepository.existsById(idx)) {
            throw new BaseException(BaseResponseStatus.NOTIFICATION_NOT_FOUND);
        }
        notificationRepository.deleteById(idx);
    }
}
