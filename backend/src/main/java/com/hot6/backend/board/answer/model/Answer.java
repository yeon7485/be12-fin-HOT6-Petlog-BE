package com.hot6.backend.board.answer.model;

import com.hot6.backend.board.answer.images.AnswerImage;
import com.hot6.backend.board.question.images.QuestionImage;
import com.hot6.backend.board.question.model.Question;
import com.hot6.backend.common.BaseEntity;
import com.hot6.backend.user.model.User;
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
public class Answer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String content;
    private boolean selected;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_idx")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx", nullable = false)
    private User user;

    @OneToMany(mappedBy = "answer")
    private List<AnswerImage> answerImageList;

}


