package com.hot6.backend.board.comment;

import com.hot6.backend.board.comment.model.Comment;
import com.hot6.backend.board.post.model.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    @Modifying
    @Transactional
    void deleteByPostIdx(Long postIdx);
}
