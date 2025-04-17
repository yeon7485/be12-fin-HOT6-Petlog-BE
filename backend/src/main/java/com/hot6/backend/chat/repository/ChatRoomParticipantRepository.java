package com.hot6.backend.chat.repository;

import com.hot6.backend.chat.model.ChatRoomParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomParticipantRepository extends JpaRepository<ChatRoomParticipant, Long>, ChatRoomParticipantRepositoryCustom {
    // ChatRoomParticipantRepositoryCustom.java
    List<Long> findChatRoomIdsByUserId(Long userId);
    Optional<ChatRoomParticipant> findByUserIdxAndChatRoomIdx(Long userIdx, Long chatRoomIdx);
}
