package com.hot6.backend.chat.service;

import com.hot6.backend.chat.model.ChatDto;
import com.hot6.backend.chat.model.ChatRoom;
import com.hot6.backend.chat.model.ChatRoomHashtag;
import com.hot6.backend.chat.repository.ChatRoomRepository;
import com.hot6.backend.user.UserService;
import com.hot6.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomParticipantService chatRoomParticipantService;
    private final ChatRoomHashtagService chatRoomHashtagService;
    private final UserService userService;

    public List<ChatDto.ChatInfo> getList() {
        return chatRoomRepository.findAll().stream().map(ChatDto.ChatInfo::from).collect(Collectors.toList());
    }

    @Transactional
    public void createChatRoom(ChatDto.CreateChatRoomRequest request, Long userIdx) {
        ChatRoom chatRoom = ChatRoom.builder()
                .cTitle(request.getTitle())
                .build();
        chatRoomRepository.save(chatRoom);
        List<ChatRoomHashtag> hashtags = new ArrayList<>();
        for(String hashtag : request.getHashtags()) {
            ChatRoomHashtag chatRoomHashtag = ChatRoomHashtag.builder()
                    .chatRoom(chatRoom)
                    .cTag(hashtag)
                    .build();
            hashtags.add(chatRoomHashtag);
        }
        chatRoomHashtagService.saveAll(hashtags);
        User findUser = userService.findUserByIdx(userIdx);
        chatRoomParticipantService.save(findUser, chatRoom);
    }

    public List<ChatDto.ChatInfo> getChatRoomByUserIdx(Long userIdx) {
        List<ChatRoom> allByParticipants = chatRoomRepository.findAllByParticipants(userIdx);
        return allByParticipants.stream().map(ChatDto.ChatInfo::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ChatDto.ChatInfo> findMyChatRooms(Long userId) {
        List<Long> roomIds = chatRoomParticipantService.findChatRoomIdsByUserId(userId);
        if (roomIds.isEmpty()) {
            return Collections.emptyList();
        }

        return chatRoomRepository.findChatRoomsWithDetailsByIds(roomIds).stream().map(ChatDto.ChatInfo::from).collect(Collectors.toList());
    }
}
