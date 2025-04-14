package com.hot6.backend.notification;

import com.hot6.backend.notification.model.NotificationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@Tag(name = "Notification", description = "실시간 알림 기능 API")
public class NotificationController {

    @Operation(summary = "내 알림 목록 조회", description = "사용자 본인의 알림 목록을 조회합니다.")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDto.NotificationElement>> getMyNotifications(
            @PathVariable Long userId) {
        List<NotificationDto.NotificationElement> list = List.of(
                NotificationDto.NotificationElement.builder()
                        .message("병원 예약 1시간 전입니다.")
                        .sentAt("2025-04-07T11:00:00")
                        .build(),
                NotificationDto.NotificationElement.builder()
                        .message("산책 일정이 1시간 뒤입니다.")
                        .sentAt("2025-04-07T15:00:00")
                        .build());

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "알림 삭제", description = "알림 ID를 기반으로 사용자의 알림을 삭제합니다.")
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<String> deleteNotification(
             @PathVariable Long notificationId) {
        return ResponseEntity.ok("알림 삭제 완료");
    }

    @Operation(summary = "실시간 알림 테스트 전송", description = "실시간 알림 테스트용 API (추후 WebSocket 연동)")
    @PostMapping("/send")
    public ResponseEntity<String> sendRealTimeNotification(@RequestBody NotificationDto.NotificationSendRequest request) {
        return ResponseEntity.ok("알림 전송 완료");
    }
}

