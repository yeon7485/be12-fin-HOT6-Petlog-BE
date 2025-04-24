package com.hot6.backend.chat.repository;

import com.hot6.backend.chat.model.ChatRoom;
import com.hot6.backend.chat.model.QChatRoom;
import com.hot6.backend.chat.model.QChatRoomHashtag;
import com.hot6.backend.chat.model.QChatRoomParticipant;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static com.hot6.backend.chat.model.QChatRoom.chatRoom;
import static com.hot6.backend.chat.model.QChatRoomHashtag.chatRoomHashtag;
import static com.hot6.backend.chat.model.QChatRoomParticipant.chatRoomParticipant;

@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<ChatRoom> findChatRoomsWithDetailsByIds(List<Long> roomIds, Pageable pageable) {
        List<ChatRoom> results = queryFactory
                .selectFrom(chatRoom)
                .distinct()
                .leftJoin(chatRoom.participants, chatRoomParticipant).fetchJoin()
                .leftJoin(chatRoom.hashtags, chatRoomHashtag).fetchJoin()
                .where(chatRoom.idx.in(roomIds))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1) // Slice: 다음 페이지 여부 확인용
                .fetch();

        boolean hasNext = results.size() > pageable.getPageSize();
        if (hasNext) results.remove(pageable.getPageSize());

        return new SliceImpl<>(results, pageable, hasNext);
    }


    @Override
    public List<ChatRoom> findByTagsWithParticipants(List<String> tagList) {
        return queryFactory
                .selectDistinct(chatRoom)
                .from(chatRoom)
                .join(chatRoom.hashtags, chatRoomHashtag) // ✅ 올바른 필드명
                .join(chatRoom.participants, chatRoomParticipant).fetchJoin() // ✅ fetchJoin으로 참여자도 함께 조회
                .where(chatRoomHashtag.cTag.in(tagList))
                .fetch();
    }

    @Override
    public List<ChatRoom> findByTitleWithParticipantsAndTags(String keyword) {
        return queryFactory
                .selectDistinct(chatRoom)
                .from(chatRoom)
                .join(chatRoom.participants, chatRoomParticipant).fetchJoin()
                .join(chatRoom.hashtags, chatRoomHashtag)
                .where(chatRoom.cTitle.containsIgnoreCase(keyword))
                .fetch();
    }
}
