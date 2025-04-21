package com.hot6.backend.board.question.images;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionImageRepository extends JpaRepository<QuestionImage, Long> {
    List<QuestionImage> findByQuestionIdx(Long questionIdx);
    void deleteByQuestionIdx(Long questionIdx);
    void deleteAllByUrlIn(List<String> urls);
}
