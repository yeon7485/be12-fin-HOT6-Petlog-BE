package com.hot6.backend.chat.service;

import com.hot6.backend.chat.model.Chat;
import com.hot6.backend.chat.model.ChatDto;
import com.hot6.backend.chat.model.ChatRoom;
import com.hot6.backend.chat.model.ChatRoomParticipant;
import com.hot6.backend.chat.repository.ChatMessageRepository;
import com.hot6.backend.common.BaseResponseStatus;
import com.hot6.backend.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    @Transactional(readOnly = true)
    public List<ChatDto.ChatElement> findChatMessages(ChatRoomParticipant chatRoomParticipant) {
        Long roomId = chatRoomParticipant.getChatRoom().getIdx();
        Long firstJoinMessageId = chatRoomParticipant.getMetaData().getFirstJoinMessageId();

        // 💡 첫 참여 메시지 ID가 null일 경우 예외 처리
        if (firstJoinMessageId == null) {
            throw new BaseException(BaseResponseStatus.CHAT_ROOM_FIRST_JOIN_ID_NULL);
        }

        List<Chat> chats = chatMessageRepository.findByChatRoomIdAndIdxGreaterThanEqualOrderByTimestampAsc(
                roomId, firstJoinMessageId
        );

        return chats.stream()
                .map(ChatDto.ChatElement::from)
                .toList();
    }

    @Transactional(readOnly = false)
    public ChatDto.ChatElement saveChatMessage(Chat chat) {
        return ChatDto.ChatElement.from(chatMessageRepository.save(chat));
    }

    @Transactional(readOnly = true)
    public Optional<Chat> findLatestChatByChatRoom(ChatRoom chatRoom) {
        return chatMessageRepository.findTopByChatRoomParticipant_ChatRoomOrderByIdxDesc(chatRoom.getIdx());
    }
}
