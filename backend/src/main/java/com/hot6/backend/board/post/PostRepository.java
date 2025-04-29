package com.hot6.backend.board.post;

import com.hot6.backend.board.post.model.BoardType;
import com.hot6.backend.board.post.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // 사용자의 작성글 목록 조회 (삭제되지 않은 것만)
    List<Post> findByUserIdxAndIsDeletedFalseOrderByCreatedAtDesc(Long userIdx);

    // 게시판 타입만으로 전체 조회 (페이징)
    Page<Post> findByBoardType(BoardType boardType, Pageable pageable);

    // 게시판 + 카테고리 + 제목 검색 (기존)
    Page<Post> findByBoardTypeAndCategoryNameAndTitleContainingIgnoreCase(
            BoardType boardType, String categoryName, String keyword, Pageable pageable
    );

    // 게시판 + 카테고리 + 작성자 검색 (기존)
    Page<Post> findByBoardTypeAndCategoryNameAndUser_NicknameContainingIgnoreCase(
            BoardType boardType, String categoryName, String keyword, Pageable pageable
    );

    // 게시판 + 카테고리 내 제목 OR 작성자 통합 검색 (신규 추가)
    Page<Post> findByBoardTypeAndCategoryNameAndTitleContainingIgnoreCaseOrBoardTypeAndCategoryNameAndUser_NicknameContainingIgnoreCase(
            BoardType boardType1, String categoryName1, String titleKeyword,
            BoardType boardType2, String categoryName2, String nicknameKeyword,
            Pageable pageable
    );

    // 게시판 + 카테고리 없이 제목 OR 작성자 통합 검색 (신규 추가)
    Page<Post> findByBoardTypeAndTitleContainingIgnoreCaseOrBoardTypeAndUser_NicknameContainingIgnoreCase(
            BoardType boardType1, String titleKeyword,
            BoardType boardType2, String nicknameKeyword,
            Pageable pageable
    );

    // 게시판 + 카테고리만으로 조회 (키워드 없이)
    Page<Post> findByBoardTypeAndCategoryName(
            BoardType boardType, String categoryName, Pageable pageable
    );
}
