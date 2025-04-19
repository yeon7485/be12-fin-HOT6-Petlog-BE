package com.hot6.backend.board.post.images;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    List<PostImage> findByPostIdx(Long postIdx);
    void deleteByPostIdx(Long postIdx);
}