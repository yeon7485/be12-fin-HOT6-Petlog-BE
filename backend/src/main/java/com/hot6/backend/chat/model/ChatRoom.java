package com.hot6.backend.chat.model;

import com.hot6.backend.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Chat_Room")
public class ChatRoom extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String cTitle;


    @OneToMany(mappedBy = "chatRoom")
    private List<ChatRoomParticipant> participants = new ArrayList<>();

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatRoomHashtag> hashtags = new ArrayList<>();
}