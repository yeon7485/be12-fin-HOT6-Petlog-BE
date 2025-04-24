package com.hot6.backend.chat.service;

import com.hot6.backend.chat.model.ChatRoom;
import com.hot6.backend.chat.model.ChatRoomHashtag;
import com.hot6.backend.chat.repository.ChatRoomHashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomHashtagService {
    private final ChatRoomHashtagRepository chatRoomHashtagRepository;

    @Transactional
    public void saveAll(List<ChatRoomHashtag> hashtags) {
        chatRoomHashtagRepository.saveAll(hashtags);
    }

    public List<ChatRoomHashtag> findByChatRoom(ChatRoom chatRoom) {
        return chatRoomHashtagRepository.findChatRoomHashtagByChatRoom(chatRoom);
    }

    public void deleteAll(List<ChatRoomHashtag> toRemove) {
        chatRoomHashtagRepository.deleteAllInBatch(toRemove);
    }
}
