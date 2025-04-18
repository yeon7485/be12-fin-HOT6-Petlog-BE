package com.hot6.backend.schedule.model;

import com.hot6.backend.common.BaseEntity;
import com.hot6.backend.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private Long categoryIdx;
    private String placeId;
    private boolean isDeleted;
    private int repeatCount;
    private LocalDate repeatEndAt;

    private LocalDateTime startAt;
    private LocalDateTime endAt;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;
}
