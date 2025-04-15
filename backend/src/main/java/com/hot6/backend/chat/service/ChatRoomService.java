package com.hot6.backend.chat.service;

import com.hot6.backend.chat.model.ChatDto;
import com.hot6.backend.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    public List<ChatDto.ChatInfo> getList() {
        return chatRoomRepository.findAll().stream().map(ChatDto.ChatInfo::from).collect(Collectors.toList());
    }
}
