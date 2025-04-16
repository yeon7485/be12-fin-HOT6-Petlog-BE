package com.hot6.backend.chat.repository;

import java.util.List;

public interface ChatRoomParticipantRepositoryCustom {
    List<Long> findChatRoomIdsByUserId(Long userId);
}
