package com.hot6.backend.board.post.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class PostDto {

    @Getter
    @Setter
    public static class PostRequest {
        private String writer;
        private String title;
        private String content;
        private String image;
        private String category;
        private String boardType; // board name으로 받음
    }

    @Getter
    @Builder
    public static class PostResponse {
        private Long idx;
        private String writer;
        private String title;
        private String content;
        private String image;
        private String category;
        private LocalDate createdAt;
        private String boardType;

        public static PostResponse from(Post post) {
            return PostResponse.builder()
                    .idx(post.getIdx())
                    .writer(post.getUser().getNickname())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .image(post.getImage())
                    .category(post.getCategory())
                    .createdAt(LocalDate.from(post.getCreatedAt()))
                    .boardType(post.getBoardType().getBoardName())
                    .build();
        }
    }
}