package com.hot6.backend.board.question;

import com.hot6.backend.board.post.model.Post;
import com.hot6.backend.board.question.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByqTitleContainingIgnoreCaseOrUserNicknameContainingIgnoreCaseOrContentContainingIgnoreCaseOrHashtagsListTagContainingIgnoreCase(
            String title, String nickname, String content, String tag
    );
    List<Question> findByUser_IdxOrderByCreatedAtDesc(Long userIdx);
}

