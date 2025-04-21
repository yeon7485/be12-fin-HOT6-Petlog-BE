package com.hot6.backend.notification;

import com.hot6.backend.notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByScheduleUserIdOrderBySentAtDesc(Long userId);
}
