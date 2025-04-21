package com.hot6.backend.notification.model;

import com.hot6.backend.schedule.model.Schedule;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class NotificationDto {

    @Getter
    @Builder
    @Schema(description = "알림 항목")
    public static class NotificationElement {

        @Schema(description = "알림 메시지", example = "병원 예약 1시간 전입니다.")
        private String message;

        @Schema(description = "알림 시간", example = "2025-04-07T11:00:00")
        private LocalDateTime sentAt;

        public Notification toEntity(Schedule schedule) {
            Notification notification = new Notification();
            notification.setMessage(this.message);

            notification.setSentAt(this.sentAt != null ? this.sentAt : LocalDateTime.now());

            notification.setSchedule(schedule);

            return notification;
        }
    }

    @Getter
    @Schema(description = "알림 전송 요청 DTO")
    public static class NotificationSendRequest {

        @Schema(description = "수신자 사용자 ID", example = "1")
        private Long userId;

        @Schema(description = "스케줄 ID", example = "101")
        private Long scheduleId;

        @Schema(description = "알림 메시지", example = "산책 일정 30분 전입니다.")
        private String message;
    }
}
