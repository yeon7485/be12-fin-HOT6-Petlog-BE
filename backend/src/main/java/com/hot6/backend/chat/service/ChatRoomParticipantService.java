package com.hot6.backend.chat.service;

import com.hot6.backend.chat.model.ChatDto;
import com.hot6.backend.chat.model.ChatRoom;
import com.hot6.backend.chat.model.ChatRoomParticipant;
import com.hot6.backend.chat.repository.ChatRoomParticipantRepository;
import com.hot6.backend.common.BaseResponseStatus;
import com.hot6.backend.common.exception.BaseException;
import com.hot6.backend.user.model.User;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
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
                        .isAdmin(true)
                        .chatRoom(chatRoom)
                        .build()
        );
    }

    public List<Long> findChatRoomIdsByUserId(Long userId) {
        return chatRoomParticipantRepository.findChatRoomIdsByUserId(userId);
    }

    public Slice<ChatDto.ChatUserInfo> getChatRoomInUsers(Long chatRoomIdx, Long lastUserId, int size) {
        return chatRoomParticipantRepository.findUsersInChatRoom(chatRoomIdx, lastUserId, size);
    }

    public ChatRoomParticipant findChatRoomParticipantOrThrow(Long chatRoomIdx,Long userIdx) {
        return chatRoomParticipantRepository.findByUserIdxAndChatRoomIdx(userIdx, chatRoomIdx)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.CHAT_ROOM_ACCESS_DENIED));
    }


    @Transactional(readOnly = false)
    public void leaveChatRoom(Long chatRoomIdx, Long idx) {
        ChatRoomParticipant chatRoomParticipant = chatRoomParticipantRepository.findByUserIdAndChatRoomIdSimple(idx, chatRoomIdx)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.CHAT_ROOM_ACCESS_DENIED));
        System.out.println("🔍 before delete");

        chatRoomParticipantRepository.delete(chatRoomParticipant);

        System.out.println("🔍 after delete");
    }
}
