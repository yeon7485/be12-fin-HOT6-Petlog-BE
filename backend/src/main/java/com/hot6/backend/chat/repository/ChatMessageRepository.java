package com.hot6.backend.chat.repository;

import com.hot6.backend.chat.model.Chat;
import com.hot6.backend.chat.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<Chat,Long> {
    @Query("SELECT c FROM Chat c " +
            "WHERE c.chatRoomParticipant.chatRoom.idx = :roomId " +
            "AND c.idx >= :startMessageId " +
            "ORDER BY c.createdAt ASC")
    List<Chat> findByChatRoomIdAndIdxGreaterThanEqualOrderByTimestampAsc(Long roomId, Long startMessageId);


    Optional<Chat> findTopByChatRoomParticipant_ChatRoomOrderByIdxDesc(ChatRoom chatRoom);
}
