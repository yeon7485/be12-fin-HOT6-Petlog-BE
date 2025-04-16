package com.hot6.backend.chat.service;

import com.hot6.backend.chat.model.ChatRoomHashtag;
import com.hot6.backend.chat.repository.ChatRoomHashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomHashtagService {
    private final ChatRoomHashtagRepository chatRoomHashtagRepository;

    public void saveAll(List<ChatRoomHashtag> hashtags) {
        chatRoomHashtagRepository.saveAll(hashtags);
    }
}
