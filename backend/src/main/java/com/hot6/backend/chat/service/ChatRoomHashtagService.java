package com.hot6.backend.chat.service;

import com.hot6.backend.chat.repository.ChatRoomHashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomHashtagService {
    private final ChatRoomHashtagRepository chatRoomHashtagRepository;
}
