package com.hot6.backend.chat.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.hot6.backend.chat.model.QChatRoomParticipant.chatRoomParticipant;

@RequiredArgsConstructor
public class ChatRoomParticipantRepositoryImpl implements ChatRoomParticipantRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Long> findChatRoomIdsByUserId(Long userId) {
        return queryFactory
                .select(chatRoomParticipant.chatRoom.idx)
                .from(chatRoomParticipant)
                .where(chatRoomParticipant.user.idx.eq(userId))
                .fetch();
    }
}
