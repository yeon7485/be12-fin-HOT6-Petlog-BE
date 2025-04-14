package com.hot6.backend.notification.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

public class NotificationDto {

    @Getter
    @Builder
    @Schema(description = "알림 항목")
    public static class NotificationElement {
        @Schema(description = "알림 메시지", example = "병원 예약 1시간 전입니다.")
        private String message;

        @Schema(description = "알림 시간", example = "2025-04-07T11:00:00")
        private String sentAt;
    }

    @Getter
    @Schema(description = "알림 전송 요청 DTO")
    public static class NotificationSendRequest {
        @Schema(description = "수신자 사용자 ID", example = "1")
        private Long userId;

        @Schema(description = "알림 메시지", example = "산책 일정 30분 전입니다.")
        private String message;
    }
}
