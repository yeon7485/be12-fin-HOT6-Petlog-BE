package com.hot6.backend.board.question.model;

import com.hot6.backend.board.hashtagQuestion.model.Hashtag_Question;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String writer;
    private String qTitle;
    private String content;
    private boolean selected;
    private String image;

    @Column(nullable = false, updatable = false, columnDefinition = "DATE")
    private LocalDate created_at;

    @Column(nullable = false, updatable = false, columnDefinition = "DATE")
    private LocalDate updated_at;

    @PrePersist
    protected void onCreate() {
        created_at = LocalDate.now();
        updated_at = LocalDate.now();
    }

    @OneToMany(mappedBy = "question")
    private List<Hashtag_Question> hashtagsList;


//    @ManyToOne
//    @JoinColumn(name="user_idx")
//    private User user;
}
