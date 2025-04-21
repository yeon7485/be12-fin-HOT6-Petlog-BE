package com.hot6.backend.schedule.model;

import com.hot6.backend.chat.model.ChatRoom;
import com.hot6.backend.category.model.Category;
import com.hot6.backend.common.BaseEntity;
import com.hot6.backend.notification.model.Notification;
import com.hot6.backend.pet.model.Pet;
import com.hot6.backend.pet.model.SharedSchedulePet;
import com.hot6.backend.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
public class Schedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String sTitle;
    private String sMemo;
    private boolean recurring;
    private String repeatCycle;
    private String placeId;
    private boolean isDeleted;
    private int repeatCount;
    private LocalDate repeatEndAt;

    private LocalDateTime startAt;
    private LocalDateTime endAt;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_idx")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "pet_idx", foreignKey = @ForeignKey(name = "FK_schedule_pet"))  // DB에 생성될 외래키 이름
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "chat_room_idx")
    private ChatRoom chatRoom;

    @OneToMany(mappedBy = "schedule")
    private List<SharedSchedulePet> sharedSchedules;

    @OneToOne(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private Notification notification;
}
