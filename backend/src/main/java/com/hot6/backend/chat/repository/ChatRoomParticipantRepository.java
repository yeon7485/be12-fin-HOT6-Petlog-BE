package com.hot6.backend.chat.repository;

import com.hot6.backend.chat.model.ChatRoom;
import com.hot6.backend.chat.model.ChatRoomParticipant;
import com.hot6.backend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomParticipantRepository extends JpaRepository<ChatRoomParticipant, Long>, ChatRoomParticipantRepositoryCustom {
    // ChatRoomParticipantRepositoryCustom.java
    List<Long> findChatRoomIdsByUserId(Long userId);
    Optional<ChatRoomParticipant> findByUserIdxAndChatRoomIdx(Long userIdx, Long chatRoomIdx);

    @Query("""
SELECT p FROM ChatRoomParticipant p
WHERE p.user.idx = :userIdx AND p.chatRoom.idx = :chatRoomIdx
""")
    Optional<ChatRoomParticipant> findByUserIdAndChatRoomIdSimple(Long userIdx,Long chatRoomIdx);

    int countByChatRoom(ChatRoom chatRoom);

    Optional<ChatRoomParticipant> findChatRoomParticipantByChatRoomAndUser(ChatRoom chatRoom, User user);

    @Query(value = "SELECT * FROM chat_room_participant WHERE chat_room_idx = :chatRoomIdx AND user_idx = :userIdx", nativeQuery = true)
    Optional<ChatRoomParticipant> findChatRoomParticipantIncludingDeleted(Long chatRoomIdx, Long userIdx);
}
