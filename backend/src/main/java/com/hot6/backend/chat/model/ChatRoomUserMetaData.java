package com.hot6.backend.chat.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomUserMetaData {
    private Long firstJoinMessageId;
    private Long lastSeenMessageId;
    private LocalDateTime joinedAt;
    private boolean isMuted;
    private boolean notificationsEnabled;
}