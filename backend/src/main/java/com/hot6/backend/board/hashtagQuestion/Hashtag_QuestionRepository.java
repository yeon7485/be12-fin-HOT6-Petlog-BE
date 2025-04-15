package com.hot6.backend.board.hashtagQuestion;

import com.hot6.backend.board.hashtagQuestion.model.Hashtag_Question;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface Hashtag_QuestionRepository extends JpaRepository<Hashtag_Question, Long> {
    List<Hashtag_Question> findByQuestionIdx(Long questionIdx);
    @Modifying
    @Transactional
    void deleteByQuestionIdx(Long questionIdx);
}
