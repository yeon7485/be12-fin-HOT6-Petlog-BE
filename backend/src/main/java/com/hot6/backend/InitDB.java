package com.hot6.backend;

import com.hot6.backend.category.model.Category;
import com.hot6.backend.category.model.CategoryRepository;
import com.hot6.backend.category.model.CategoryType;
import com.hot6.backend.chat.model.*;
import com.hot6.backend.chat.repository.ChatRepository;
import com.hot6.backend.chat.repository.ChatRoomHashtagRepository;
import com.hot6.backend.chat.repository.ChatRoomParticipantRepository;
import com.hot6.backend.chat.repository.ChatRoomRepository;
import com.hot6.backend.pet.PetRepository;
import com.hot6.backend.pet.model.Pet;
import com.hot6.backend.pet.model.SharedSchedulePet;
import com.hot6.backend.schedule.ScheduleRepository;
import com.hot6.backend.schedule.model.Schedule;
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

        private final PetRepository petRepository;
        private final ScheduleRepository scheduleRepository;
        private final UserRepository userRepository;
        private final ChatRoomRepository chatRoomRepository;
        private final ChatRoomHashtagRepository hashtagRepository;
        private final ChatRoomParticipantRepository participantRepository;
        private final ChatRepository chatRepository;
        private final CategoryRepository categoryRepository;

        @PersistenceContext
        private EntityManager em;

        public void init() {
            List<User> users = createUsers(10);
            Map<User, List<Pet>> pets = createPets(users);
            List<ChatRoom> chatRooms = createChatRooms(10);
            createHashtags(chatRooms, 30);
            List<ChatRoomParticipant> participants = createChatRoomParticipants(users, chatRooms);
            createSchedules(chatRooms, participants, pets);
            createChatMessages(chatRooms, participants);
            createCategory();
        }

        private List<Category> createCategory() {
            List<Category> categories = new ArrayList<>();

            categories.add(Category.builder()
                    .name("병원")
                    .color("#00C9CD")
                    .type(CategoryType.SCHEDULE)
                    .build());
            categories.add(Category.builder()
                    .name("미용")
                    .color("#E6B0BD")
                    .type(CategoryType.SCHEDULE)
                    .build());
            categories.add(Category.builder()
                    .name("산책")
                    .color("#65924D")
                    .type(CategoryType.SCHEDULE)
                    .build());

            categories.add(Category.builder()
                    .name("체중")
                    .color("#00C9CD")
                    .type(CategoryType.DAILY_RECORD)
                    .build());
            categories.add(Category.builder()
                    .name("이상현상")
                    .color("#B36063")
                    .type(CategoryType.DAILY_RECORD)
                    .build());

            return categoryRepository.saveAll(categories);
        }

        private List<User> createUsers(int count) {
            List<User> users = new ArrayList<>();
            users.add(User.builder()
                    .email("user" + 0 + "@test.com")
                    .password("$2a$10$.QJ.leSKCQXX9Tn8pCipIOy8F.XhB8o0Gl1AFIRBN10L0LCFiJSB2") // bcrypt
                    .nickname("User" + 0)
                    .userProfileImage("https://example.com/img" + 0 + ".png")
                    .userType(UserType.ADMIN)
                    .enabled(true)
                    .build());
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

        private Map<User, List<Pet>> createPets(List<User> users) {
            Map<User, List<Pet>> petMap = new HashMap<>();
            List<Pet> allPets = new ArrayList<>();
            for (User user : users) {
                List<Pet> pets = new ArrayList<>();
                for (int i = 1; i <= 2; i++) { // 유저당 2마리
                    Pet pet = Pet.builder()
                            .user(user)
                            .name(user.getNickname() + "의 반려동물" + i)
                            .birthDate(LocalDateTime.now().minusYears(2).minusMonths(i).toString())
                            .build();
                    pets.add(pet);
                    allPets.add(pet);
                }
                petMap.put(user, pets);
            }
            petRepository.saveAll(allPets);
            return petMap;
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
                        .cTag("hashtag" + i)
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

        private void createSchedules(List<ChatRoom> chatRooms,
                                     List<ChatRoomParticipant> participants,
                                     Map<User, List<Pet>> petMap) {
            Random random = new Random();
            List<Schedule> schedules = new ArrayList<>();
            List<SharedSchedulePet> sharedSchedulePets = new ArrayList<>();

            for (ChatRoom room : chatRooms) {
                List<ChatRoomParticipant> roomParticipants = participants.stream()
                        .filter(p -> p.getChatRoom().equals(room))
                        .toList();

                int scheduleCount = random.nextInt(3) + 1; // 1~3개

                for (int i = 0; i < scheduleCount; i++) {
                    ChatRoomParticipant creator = roomParticipants.get(random.nextInt(roomParticipants.size()));
                    User owner = creator.getUser();

                    LocalDateTime start = LocalDateTime.now().plusDays(random.nextInt(30));
                    LocalDateTime end = start.plusHours(1);

                    Schedule schedule = Schedule.builder()
                            .chatRoom(room)
                            .sTitle("일정 " + UUID.randomUUID())
                            .startAt(start)
                            .endAt(end)
                            .placeId("장소 " + (i + 1))
                            .sMemo("메모 " + (i + 1))
                            .build();

                    em.persist(schedule);
                    schedules.add(schedule);

                    // 펫 연결
                    List<Pet> pets = petMap.get(owner);
                    Collections.shuffle(pets);
                    int petCount = Math.min(pets.size(), random.nextInt(2) + 1); // 1~2마리

                    for (int j = 0; j < petCount; j++) {
                        SharedSchedulePet shared = SharedSchedulePet.builder()
                                .schedule(schedule)
                                .pet(pets.get(j))
                                .build();
                        em.persist(shared);
                        sharedSchedulePets.add(shared);
                    }
                }
            }

            log.info("✅ 일정 {}개와 공유 펫 {}개 생성 완료", schedules.size(), sharedSchedulePets.size());
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
                            .type(ChatMessageType.TEXT)
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
