package com.hot6.backend.board.post;

import com.hot6.backend.board.post.model.BoardType;
import com.hot6.backend.board.post.model.Post;
import com.hot6.backend.category.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByBoardType(BoardType boardType);

    List<Post> findByBoardTypeAndCategoryName(BoardType boardType, String categoryName);

    List<Post> findByBoardTypeAndCategoryNameAndTitleContainingIgnoreCase(BoardType boardType, String categoryName, String keyword);

    List<Post> findByBoardTypeAndCategoryNameAndUser_NicknameContainingIgnoreCase(BoardType boardType, String categoryName, String keyword);

    List<Post> findByUserIdxAndIsDeletedFalseOrderByCreatedAtDesc(Long userIdx);

    Page<Post> findByBoardType(BoardType boardType, Pageable pageable);
}