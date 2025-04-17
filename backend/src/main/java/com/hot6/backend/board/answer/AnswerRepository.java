package com.hot6.backend.board.answer;

import com.hot6.backend.board.answer.model.Answer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestion_Idx(Long questionIdx);
    @Modifying
    @Transactional
    void deleteByQuestionIdx(Long questionIdx);

    int countByQuestionIdx(Long questionId);
}
