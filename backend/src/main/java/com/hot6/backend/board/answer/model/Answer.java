package com.hot6.backend.board.answer.model;

import com.hot6.backend.board.question.model.Question;
import com.hot6.backend.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String content;
    private boolean selected;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx", nullable = false)
    private User user;
}


