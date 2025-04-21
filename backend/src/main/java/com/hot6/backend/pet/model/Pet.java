package com.hot6.backend.pet.model;

import com.hot6.backend.record.model.DailyRecord;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hot6.backend.board.post.model.Post;
import com.hot6.backend.board.question.model.Question;
import com.hot6.backend.schedule.model.Schedule;
import com.hot6.backend.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String name;
    private String breed;
    private String gender;
    private String birthDate;
    private String profileImageUrl;
    private boolean isNeutering;
    private String specificInformation;
    @Enumerated(EnumType.STRING)
    private PetStatus status;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PetImage> petImageList = new ArrayList<>();


    @OneToMany(mappedBy = "pet")
    private List<DailyRecord> dailyRecordList = new ArrayList<>();

    @OneToMany(mappedBy = "pet", cascade = CascadeType.REMOVE)
    private List<Schedule> schedules;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "pet")
    private List<SharedSchedulePet> sharedSchedules;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_idx", nullable = true) // 게시글 없는 상태에서도 가능하게
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_idx")
    private Question question;

}
