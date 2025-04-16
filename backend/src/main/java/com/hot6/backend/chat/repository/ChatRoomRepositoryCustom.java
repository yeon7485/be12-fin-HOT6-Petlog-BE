package com.hot6.backend.chat.repository;

import com.hot6.backend.chat.model.ChatRoom;

import java.util.List;

public interface ChatRoomRepositoryCustom {
    List<ChatRoom> findChatRoomsWithDetailsByIds(List<Long> roomIds);
}
