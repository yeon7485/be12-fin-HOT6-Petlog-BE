package com.hot6.backend.board.comment.model;

import lombok.Builder;
import lombok.Getter;

public class CommentDto {

    @Getter
    @Builder
    public static class CommentElement{
        private String comment;
    }

    @Getter
    public static class RegisterRequest {
        private Long userIdx;
        private String comment;
    }
}
