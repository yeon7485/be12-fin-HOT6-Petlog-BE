package com.hot6.backend.chat.repository;

import com.hot6.backend.chat.model.ChatRoomHashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomHashtagRepository extends JpaRepository<ChatRoomHashtag, Long> {
}
