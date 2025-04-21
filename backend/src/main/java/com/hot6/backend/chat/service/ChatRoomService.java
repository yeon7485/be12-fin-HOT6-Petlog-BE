package com.hot6.backend.chat.service;

import com.hot6.backend.chat.model.*;
import com.hot6.backend.chat.repository.ChatRoomRepository;
import com.hot6.backend.common.BaseResponseStatus;
import com.hot6.backend.common.exception.BaseException;
import com.hot6.backend.pet.PetService;
import com.hot6.backend.pet.model.Pet;
import com.hot6.backend.schedule.ScheduleService;
import com.hot6.backend.schedule.model.Schedule;
import com.hot6.backend.user.UserService;
import com.hot6.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomParticipantService chatRoomParticipantService;
    private final ChatRoomHashtagService chatRoomHashtagService;
    private final UserService userService;
    private final ChatMessageService chatMessageService;
    private final ScheduleService scheduleService;
    private final PetService petService;

    public Slice<ChatDto.ChatRoomListDto> getList(Long userIdx, Pageable pageable) {
        Slice<ChatRoom> chatRooms = chatRoomRepository.findAllWithSlice(pageable);
        //ChatInfo 에 정보 추가(isParticipating)
        return chatRooms.map(room -> ChatDto.ChatRoomListDto.from(room, userIdx));
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

    @Transactional(readOnly = true)
    public Slice<ChatDto.MyChatRoomListDto> findMyChatRooms(Long userId, Pageable pageable) {
        List<Long> roomIds = chatRoomParticipantService.findChatRoomIdsByUserId(userId);
        if (roomIds.isEmpty()) {
            return new SliceImpl<>(Collections.emptyList(), pageable, false);
        }

        // Slice를 리턴하는 커스텀 레포지토리 메서드 사용
        Slice<ChatRoom> rooms = chatRoomRepository.findChatRoomsWithDetailsByIds(roomIds, pageable);

        return rooms.map(ChatDto.MyChatRoomListDto::from);
    }

    public List<ChatDto.ChatElement> getChatMessages(Long chatRoomIdx, Long userIdx) {
        ChatRoomParticipant chatRoomParticipant = chatRoomParticipantService.findChatRoomParticipantOrThrow(chatRoomIdx, userIdx);
        return chatMessageService.findChatMessages(chatRoomParticipant);
    }

    public ChatDto.ChatRoomDetailInfo getChatRoomInfo(Long chatRoomIdx) {
        ChatRoom chatRoom = chatRoomRepository.findWithParticipantsAndHashtagsById(chatRoomIdx)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.CHAT_ROOM_NOT_FOUND));

        return ChatDto.ChatRoomDetailInfo.from(chatRoom);
    }

    @Transactional
    public ChatDto.ChatElement saveSendMessage(Long roomIdx, Long sender, ChatDto.ChatMessageDto chatMessageDto) {
        ChatRoom chatRoom = chatRoomRepository.findByIdx(roomIdx)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.CHAT_ROOM_NOT_FOUND));
        ChatRoomParticipant chatRoomParticipant = chatRoomParticipantService.findChatRoomParticipantOrThrow(chatRoom.getIdx(), sender);

        Chat chat = Chat.builder()
                .chatRoomParticipant(chatRoomParticipant)
                .type(ChatMessageType.from(chatMessageDto.getType()))
                .message(chatMessageDto.getText()) // 또는 dto.getMessage() 등
                .build();

        return chatMessageService.saveChatMessage(chat);
    }

    public List<ChatDto.ChatRoomScheduleElement> getChatRoomSchedule(Long chatRoomIdx) {
        return scheduleService.getALLScheduleByChatRoom(chatRoomIdx);
    }

    public void createChatRoomSchedule(ChatDto.CreateChatRoomScheduleRequest dto, Long chatRoomIdx, User user) {
        ChatRoom chatRoom = chatRoomRepository.findByIdx(chatRoomIdx).orElseThrow(() -> new BaseException(BaseResponseStatus.CHAT_ROOM_NOT_FOUND));
        scheduleService.createChatRoomSchedule(dto, chatRoom, user);
    }


    public ChatDto.ChatRoomScheduleDetailResponse getChatRoomScheduleDetail(Long chatRoomIdx, Long scheduleIdx ,User user) {
        ChatRoom chatRoom = chatRoomRepository.findByIdx(chatRoomIdx).orElseThrow(() -> new BaseException(BaseResponseStatus.CHAT_ROOM_NOT_FOUND));
        Schedule schedule = scheduleService.getSchedule(scheduleIdx);
        List<User> usersInChatRoomsSchedule = scheduleService.findChatRoomUsersParticipatingInSchedule(scheduleIdx);


        // 현재 사용자의 참여 여부
        boolean isParticipating = usersInChatRoomsSchedule.stream()
                .anyMatch(u -> u.getIdx().equals(user.getIdx()));

        List<Pet> usersPet = isParticipating == false ? petService.findByUser(user) : Collections.emptyList();

        // 응답 DTO 생성
        return ChatDto.ChatRoomScheduleDetailResponse.from(schedule,usersInChatRoomsSchedule,isParticipating,usersPet);
    }
}
