package com.hot6.backend.chat.model;

import com.hot6.backend.common.BaseEntity;
import com.hot6.backend.user.model.User;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Chat_Room_Participant")
public class ChatRoomParticipant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private Boolean isAdmin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_idx")
    private ChatRoom chatRoom;

    @OneToMany(mappedBy = "chatRoomParticipant")
    private List<Chat> chats = new ArrayList<>();

    @Type(JsonType.class)
    @Column(name = "meta_data", columnDefinition = "json")
    private ChatRoomUserMetaData metaData;
}