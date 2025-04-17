package com.hot6.backend.chat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomUserMetaData {
    private Long firstJoinMessageId;
    private Long lastSeenMessageId;
    private LocalDateTime joinedAt;
    private boolean isMuted;
    private boolean notificationsEnabled;
}