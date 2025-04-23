package com.hot6.backend.board.answer.images;

import com.hot6.backend.board.answer.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerImageRepository extends JpaRepository<AnswerImage, Long> {
    List<AnswerImage> findByAnswerIdx(Long answerIdx);

    void deleteByAnswerIdx(Long answerIdx);

    List<AnswerImage> findByAnswer(Answer answer);
}