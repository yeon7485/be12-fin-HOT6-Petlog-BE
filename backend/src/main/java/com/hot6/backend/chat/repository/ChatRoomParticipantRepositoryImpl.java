package com.hot6.backend.chat.repository;

import com.hot6.backend.chat.model.ChatDto;
import com.hot6.backend.chat.model.QChatDto_ChatUserInfo;
import com.hot6.backend.chat.model.QChatRoomParticipant;
import com.hot6.backend.user.model.QUser;
import com.hot6.backend.user.model.UserDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

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

    @Override
    public Slice<ChatDto.ChatUserInfo> findUsersInChatRoom(Long chatRoomId, Long lastUserId, int size)  {
        QChatRoomParticipant participant = QChatRoomParticipant.chatRoomParticipant;
        QUser user = QUser.user;

        List<ChatDto.ChatUserInfo> results = queryFactory
                .select(new QChatDto_ChatUserInfo(user.idx, user.nickname, user.userProfileImage))
                .from(participant)
                .join(participant.user, user)
                .where(
                        participant.chatRoom.idx.eq(chatRoomId),
                        lastUserId != null ? user.idx.lt(lastUserId) : null
                )
                .orderBy(user.idx.desc())
                .limit(size + 1)
                .fetch();

        boolean hasNext = results.size() > size;
        if (hasNext) results.remove(size);

        return new SliceImpl<>(results, PageRequest.of(0, size), hasNext);
    }
}
