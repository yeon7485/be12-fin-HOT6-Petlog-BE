package com.hot6.backend.chat.repository;

import com.hot6.backend.chat.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<Chat,Long> {
    @Query("SELECT c FROM Chat c " +
            "WHERE c.chatRoomParticipant.chatRoom.idx = :roomId " +
            "AND c.idx >= :startMessageId " +
            "ORDER BY c.createdAt ASC")
    List<Chat> findByChatRoomIdAndIdxGreaterThanEqualOrderByTimestampAsc(Long roomId, Long startMessageId);

}
