package com.hot6.backend.chat.service;

import com.hot6.backend.chat.model.*;
import com.hot6.backend.chat.repository.ChatMessageRepository;
import com.hot6.backend.chat.repository.ChatRoomParticipantRepository;
import com.hot6.backend.common.BaseResponseStatus;
import com.hot6.backend.common.exception.BaseException;
import com.hot6.backend.user.model.User;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomParticipantService {
    private final ChatRoomParticipantRepository chatRoomParticipantRepository;
    private final ChatMessageService chatMessageService;
    private final ChatMessageRepository chatMessageRepository;

    @Transactional(readOnly = false)
    public void save(User user, ChatRoom chatRoom) {
        // ✅ 시스템 메시지 저장 후 저장된 chat 객체 반환
        Chat chat = chatMessageRepository.save(
                Chat.builder()
                        .type(ChatMessageType.TEXT)
                        .message(user.getNickname() + " 님이 채팅방을 생성하셨습니다!")
                        .build()
        );

        // ✅ 참여자 먼저 저장 (metaData 없이)
        ChatRoomParticipant chatRoomParticipant = chatRoomParticipantRepository.save(
                ChatRoomParticipant.builder()
                        .user(user)
                        .chatRoom(chatRoom)
                        .isAdmin(true)
                        .build()
        );

        // ✅ 이후 메타데이터 설정
        chatRoomParticipant.setMetaData(
                ChatRoomUserMetaData.builder()
                        .firstJoinMessageId(chat.getIdx())
                        .lastSeenMessageId(chat.getIdx())
                        .joinedAt(LocalDateTime.now())
                        .isMuted(false)
                        .notificationsEnabled(true)
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public List<Long> findChatRoomIdsByUserId(Long userId) {
        return chatRoomParticipantRepository.findChatRoomIdsByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Slice<ChatDto.ChatUserInfo> getChatRoomInUsers(Long chatRoomIdx, Long lastUserId, int size) {
        return chatRoomParticipantRepository.findUsersInChatRoom(chatRoomIdx, lastUserId, size);
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = false)
    public void join(User user, ChatRoom chatRoom) {
        Optional<Chat> latestChat = chatMessageService.findLatestChatByChatRoom(chatRoom);
        Long messageIdx = latestChat.map(Chat::getIdx).orElse(null);

        // metaData 공통 생성
        ChatRoomUserMetaData metaData = ChatRoomUserMetaData.builder()
                .firstJoinMessageId(messageIdx)
                .lastSeenMessageId(messageIdx)
                .joinedAt(LocalDateTime.now())
                .isMuted(false)
                .notificationsEnabled(true)
                .build();

        // participant를 만들거나 가져온다
        ChatRoomParticipant participant = chatRoomParticipantRepository.findChatRoomParticipantIncludingDeleted(chatRoom.getIdx(), user.getIdx())
                .map(existing -> {
                    existing.setMetaData(metaData);
                    existing.setDeleted(false);
                    return existing;
                })
                .orElseGet(() -> ChatRoomParticipant.builder()
                        .user(user)
                        .chatRoom(chatRoom)
                        .metaData(metaData)
                        .isAdmin(false)
                        .build()
                );

        // 마지막에 단 한번 저장
        chatRoomParticipantRepository.save(participant);
    }

    @Transactional(readOnly = true)
    public int countByChatRoom(ChatRoom chatRoom) {
        return chatRoomParticipantRepository.countByChatRoom(chatRoom);
    }

    @Transactional(readOnly = true)
    public ChatRoomParticipant findByChatRoomAndUser(ChatRoom chatRoom, User user) {
        return chatRoomParticipantRepository.findChatRoomParticipantByChatRoomAndUser(chatRoom, user).orElseThrow(() -> new BaseException(BaseResponseStatus.CHAT_ROOM_USER_NOT_FOUNT));
    }
}
