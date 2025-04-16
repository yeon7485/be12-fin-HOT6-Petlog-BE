package com.hot6.backend.chat.repository;

import com.hot6.backend.chat.model.ChatDto;
import com.hot6.backend.user.model.UserDto;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ChatRoomParticipantRepositoryCustom {
    List<Long> findChatRoomIdsByUserId(Long userId);
    public Slice<ChatDto.ChatUserInfo> findUsersInChatRoom(Long chatRoomId, Long lastUserId, int size);
}
