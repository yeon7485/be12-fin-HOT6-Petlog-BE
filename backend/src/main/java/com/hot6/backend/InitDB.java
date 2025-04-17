package com.hot6.backend;

import com.hot6.backend.chat.model.*;
import com.hot6.backend.chat.repository.ChatRepository;
import com.hot6.backend.chat.repository.ChatRoomHashtagRepository;
import com.hot6.backend.chat.repository.ChatRoomParticipantRepository;
import com.hot6.backend.chat.repository.ChatRoomRepository;
import com.hot6.backend.user.UserRepository;
import com.hot6.backend.user.model.User;
import com.hot6.backend.user.model.UserType;
import com.vladmihalcea.hibernate.type.json.internal.JacksonUtil;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
//@Profile("dev") // 개발 환경에서만 실행
@Component
@RequiredArgsConstructor
public class InitDB {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.init();
    }

    @Service
    @Transactional
    @RequiredArgsConstructor
    public static class InitService {

        private final UserRepository userRepository;
        private final ChatRoomRepository chatRoomRepository;
        private final ChatRoomHashtagRepository hashtagRepository;
        private final ChatRoomParticipantRepository participantRepository;
        private final ChatRepository chatRepository;

        @PersistenceContext
        private EntityManager em;

        public void init() {
            List<User> users = createUsers(1000);
            List<ChatRoom> chatRooms = createChatRooms(100);
            createHashtags(chatRooms, 300);
            List<ChatRoomParticipant> participants = createChatRoomParticipants(users, chatRooms);
            createChatMessages(chatRooms, participants);
        }

        private List<User> createUsers(int count) {
            List<User> users = new ArrayList<>();
            for (int i = 1; i <= count; i++) {
                users.add(User.builder()
                        .email("user" + i + "@test.com")
                        .password("$2a$10$.QJ.leSKCQXX9Tn8pCipIOy8F.XhB8o0Gl1AFIRBN10L0LCFiJSB2") // bcrypt
                        .nickname("User" + i)
                        .userProfileImage("https://example.com/img" + i + ".png")
                        .userType(UserType.USER)
                                .enabled(true)
                        .build());
            }
            return userRepository.saveAll(users);
        }

        private List<ChatRoom> createChatRooms(int count) {
            List<ChatRoom> chatRooms = new ArrayList<>();
            for (int i = 1; i <= count; i++) {
                chatRooms.add(ChatRoom.builder()
                        .cTitle("Chat Room " + i)
                        .build());
            }
            return chatRoomRepository.saveAll(chatRooms);
        }

        private void createHashtags(List<ChatRoom> chatRooms, int hashtagCount) {
            List<ChatRoomHashtag> hashtags = new ArrayList<>();
            for (int i = 1; i <= hashtagCount; i++) {
                ChatRoom room = chatRooms.get(i % chatRooms.size());
                hashtags.add(ChatRoomHashtag.builder()
                        .chatRoom(room)
                        .cTag("#hashtag" + i)
                        .build());
            }
            hashtagRepository.saveAll(hashtags);
        }

        private List<ChatRoomParticipant> createChatRoomParticipants(List<User> users, List<ChatRoom> chatRooms) {
            List<ChatRoomParticipant> participants = new ArrayList<>();
            Random random = new Random();

            for (User user : users) {
                int roomCount = random.nextInt(10) + 1;
                Set<ChatRoom> joinedRooms = new HashSet<>();

                while (joinedRooms.size() < roomCount) {
                    ChatRoom randomRoom = chatRooms.get(random.nextInt(chatRooms.size()));
                    joinedRooms.add(randomRoom);
                }

                for (ChatRoom room : joinedRooms) {
                    ChatRoomUserMetaData meta = new ChatRoomUserMetaData(null, null, LocalDateTime.now(), false, true);

                    ChatRoomParticipant participant = ChatRoomParticipant.builder()
                            .user(user)
                            .chatRoom(room)
                            .isAdmin(false)
                            .metaData(meta)
                            .build();

                    participants.add(participant);
                }
            }

            return participantRepository.saveAll(participants);
        }

        private void createChatMessages(List<ChatRoom> chatRooms, List<ChatRoomParticipant> participants) {
            Random random = new Random();
            long messageCounter = 1L;

            List<Chat> batchChats = new ArrayList<>();

            for (ChatRoom room : chatRooms) {
                List<ChatRoomParticipant> roomParticipants = participants.stream()
                        .filter(p -> p.getChatRoom().equals(room))
                        .toList();

                int messageCount = random.nextInt(201) + 100; // 100~300개 메시지

                for (int i = 0; i < messageCount; i++) {
                    ChatRoomParticipant sender = roomParticipants.get(random.nextInt(roomParticipants.size()));

                    Chat chat = Chat.builder()
                            .chatRoomParticipant(sender)
                            .message("Message " + messageCounter + " from " + sender.getUser().getNickname())
                            .cIsRead(false)
                            .build();

                    // 최초 참여 메시지는 개별 save() → participant의 metaData 업데이트용
                    ChatRoomUserMetaData meta = sender.getMetaData();
                    if (meta.getFirstJoinMessageId() == null) {
                        Chat saved = chatRepository.save(chat); // 바로 DB 반영
                        meta.setFirstJoinMessageId(saved.getIdx());
                        meta.setLastSeenMessageId(saved.getIdx());
                        sender.setMetaData(meta);
                        participantRepository.save(sender);
                    } else {
                        batchChats.add(chat); // 일괄 저장할 메시지에 포함
                    }

                    messageCounter++;
                }
            }

            // 💾 메시지 일괄 저장
            chatRepository.saveAll(batchChats);
        }

    }
}
