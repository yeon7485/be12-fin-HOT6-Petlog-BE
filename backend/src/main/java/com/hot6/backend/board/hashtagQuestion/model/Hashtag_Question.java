package com.hot6.backend.board.hashtagQuestion.model;

import com.hot6.backend.board.question.model.Question;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hashtag_Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String tag;

    @Column(nullable = false, updatable = false, columnDefinition = "DATE")
    private LocalDate created_at;

    @Column(nullable = false, updatable = false, columnDefinition = "DATE")
    private LocalDate updated_at;

    @PrePersist
    protected void onCreate() {
        created_at = LocalDate.now();
        updated_at = LocalDate.now();
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_idx")
    private Question question;
}
