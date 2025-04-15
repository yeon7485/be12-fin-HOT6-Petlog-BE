package com.hot6.backend.board.post;

import com.hot6.backend.board.post.model.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardTypeRepository extends JpaRepository<BoardType, Long> {
    Optional<BoardType> findByBoardName(String boardName);
}
