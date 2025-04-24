package com.hot6.backend.board.answer;

import com.hot6.backend.board.answer.model.Answer;
import com.hot6.backend.board.question.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestion_Idx(Long questionIdx);

    List<Answer> findByUser_IdxOrderByCreatedAtDesc(Long userId);

    int countByQuestionIdx(Long questionIdx);

    int countByQuestion(Question question);
}
