package com.hot6.backend.board.comment.model;



import com.hot6.backend.board.post.model.BoardType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class CommentDto {

    @Getter
    @Setter
    public static class CommentRequest {
        private Long postIdx;
        private String content;
    }

    @Getter
    @Builder
    public static class CommentResponse {
        private Long idx;
        private String writer;
        private Long userIdx;
        private String content;
        private Long postIdx;
        private String boardName; 
        private LocalDate createdAt;
        private String profileImageUrl;

        public static CommentResponse from(Comment comment) {
            return CommentResponse.builder()
                    .idx(comment.getIdx())
                    .writer(comment.getUser().getNickname())
                    .userIdx(comment.getUser().getIdx())
                    .content(comment.getContent())
                    .postIdx(comment.getPost().getIdx())
                    .boardName(comment.getPost().getBoardType().getBoardName())
                    .createdAt(LocalDate.from(comment.getCreatedAt()))
                    .profileImageUrl(comment.getUser().getUserProfileImage())
                    .build();
        }
    }
}
