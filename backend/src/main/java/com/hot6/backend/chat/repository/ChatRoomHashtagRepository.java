package com.hot6.backend.chat.repository;

import com.hot6.backend.chat.model.ChatRoom;
import com.hot6.backend.chat.model.ChatRoomHashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomHashtagRepository extends JpaRepository<ChatRoomHashtag, Long> {
    List<ChatRoomHashtag> findChatRoomHashtagByChatRoom(ChatRoom chatRoom);
}
