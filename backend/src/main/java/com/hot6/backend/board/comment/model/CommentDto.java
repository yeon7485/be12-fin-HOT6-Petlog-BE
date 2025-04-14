package com.hot6.backend.board.comment.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

public class CommentDto {

    @Getter
    @Builder
    @Schema(description = "댓글 요소 응답 DTO")
    public static class CommentElement {
        @Schema(description = "댓글 내용", example = "정말 유익한 정보 감사합니다!")
        private String comment;
    }

    @Getter
    @Schema(description = "댓글 등록 요청 DTO")
    public static class CommentRegisterRequest {
        @Schema(description = "작성자 사용자 ID", example = "1")
        private Long userIdx;

        @Schema(description = "댓글 내용", example = "좋은 글 감사합니다.")
        private String comment;
    }

    @Getter
    @Schema(description = "댓글 수정 요청 DTO")
    public static class UpdateCommentRequest {
        @Schema(description = "수정할 댓글 내용", example = "내용을 조금 더 추가했어요!")
        private String comment;
    }
}
