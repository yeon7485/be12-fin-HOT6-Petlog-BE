package com.hot6.backend.chat.service;

import com.hot6.backend.chat.model.ChatRoom;
import com.hot6.backend.chat.model.ChatRoomParticipant;
import com.hot6.backend.chat.repository.ChatRoomParticipantRepository;
import com.hot6.backend.user.model.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomParticipantService {
    private final ChatRoomParticipantRepository chatRoomParticipantRepository;

    @Transactional
    public void save(User user, ChatRoom chatRoom) {
        chatRoomParticipantRepository.save(
                ChatRoomParticipant.builder()
                        .user(user)
                        .chatRoom(chatRoom)
                        .build()
        );
    }

    public List<Long> findChatRoomIdsByUserId(Long userId) {
        return chatRoomParticipantRepository.findChatRoomIdsByUserId(userId);
    }
}
