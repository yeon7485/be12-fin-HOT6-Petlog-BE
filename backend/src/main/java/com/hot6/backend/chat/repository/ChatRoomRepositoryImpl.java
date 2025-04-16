package com.hot6.backend.chat.repository;

import com.hot6.backend.chat.model.ChatRoom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.hot6.backend.chat.model.QChatRoom.chatRoom;
import static com.hot6.backend.chat.model.QChatRoomHashtag.chatRoomHashtag;
import static com.hot6.backend.chat.model.QChatRoomParticipant.chatRoomParticipant;

@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ChatRoom> findChatRoomsWithDetailsByIds(List<Long> roomIds) {
        return queryFactory
                .selectFrom(chatRoom)
                .distinct()
                .leftJoin(chatRoom.participants, chatRoomParticipant).fetchJoin()
                .leftJoin(chatRoom.hashtags, chatRoomHashtag).fetchJoin()
                .where(chatRoom.idx.in(roomIds))
                .fetch();
    }
}
