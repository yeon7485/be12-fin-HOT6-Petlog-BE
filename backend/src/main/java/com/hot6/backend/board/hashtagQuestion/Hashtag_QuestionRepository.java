package com.hot6.backend.board.hashtagQuestion;

import com.hot6.backend.board.hashtagQuestion.model.Hashtag_Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Hashtag_QuestionRepository extends JpaRepository<Hashtag_Question, Long> {
    List<Hashtag_Question> findByQuestionIdx(Long questionIdx);
}
