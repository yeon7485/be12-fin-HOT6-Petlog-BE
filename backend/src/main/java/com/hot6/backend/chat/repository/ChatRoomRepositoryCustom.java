package com.hot6.backend.chat.repository;

import com.hot6.backend.chat.model.ChatRoom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ChatRoomRepositoryCustom {
    Slice<ChatRoom> findChatRoomsWithDetailsByIds(List<Long> roomIds, Pageable pageable);
    
}
