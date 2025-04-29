package com.hot6.backend.board.comment;

import com.hot6.backend.board.answer.model.Answer;
import com.hot6.backend.board.comment.model.Comment;
import com.hot6.backend.board.post.model.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostOrderByCreatedAtDesc(Post post);
    @Modifying
    @Transactional
    void deleteByPostIdx(Long questionIdx);

    List<Comment> findByUser_IdxOrderByCreatedAtDesc(Long userIdx);
}
