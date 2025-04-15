package com.hot6.backend.board.post;

import com.hot6.backend.board.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByBoardType(BoardType boardType);
    List<Post> findByBoardTypeAndCategory(BoardType boardType, String category);
    List<Post> findByBoardTypeAndCategoryAndTitleContainingIgnoreCase(BoardType boardType, String category, String keyword);
}
