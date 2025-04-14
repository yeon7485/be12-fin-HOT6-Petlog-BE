package com.hot6.backend.board.model;

import com.hot6.backend.board.comment.model.CommentDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class BoardDto {

    @Getter
    @Schema(description = "게시글 카테고리 리스트")
    public static class CategoryList {
        @Schema(description = "카테고리 ID", example = "1")
        private Long categoryIdx;

        @Schema(description = "카테고리 이름", example = "자유 게시판")
        private String category;
    }

    @Getter
    @Schema(description = "게시글 생성 요청")
    public static class BoardCreateRequest {
        @Schema(description = "게시글 제목", example = "강아지 분양합니다")
        private String title;

        @Schema(description = "게시글 내용", example = "3개월 된 푸들 분양해요")
        private String content;
    }

    @Getter
    @Schema(description = "게시글 수정 요청")
    public static class BoardUpdateRequest {
        @Schema(description = "수정할 제목", example = "푸들 분양 (수정)")
        private String title;

        @Schema(description = "수정할 내용", example = "수정된 내용입니다")
        private String content;
    }

    @Getter
    @Builder
    @Schema(description = "게시글 상세 응답")
    public static class BoardDetailResponse {
        @Schema(description = "게시글 ID", example = "1")
        private Long idx;

        @Schema(description = "제목", example = "강아지 분양해요")
        private String title;

        @Schema(description = "내용", example = "3개월 된 푸들이에요")
        private String content;

        @Schema(description = "작성자 이름", example = "user01")
        private String authorName;

        @Schema(description = "작성일", example = "2025-04-07")
        private String createdAt;

        @Schema(description = "댓글 리스트")
        private List<CommentDto.CommentElement> comments;
    }

    @Getter
    @Builder
    @Schema(description = "게시글 요약 정보")
    public static class BoardInfo {
        @Schema(description = "게시글 ID", example = "1")
        private Long idx;

        @Schema(description = "게시글 제목", example = "Q&A 질문")
        private String title;

        @Schema(description = "작성자", example = "user01")
        private String authorName;

        @Schema(description = "카테고리", example = "DOG")
        private BoardCategory boardCategory;

        @Schema(description = "이미지 url", example = "http://www.test.com")
        private String imageUrl;

        @Schema(description = "작성일", example = "2025-04-07")
        private String createdAt;
    }

    @Getter
    @Builder
    @Schema(description = "카테고리 응답")
    public static class CategoryResponse {
        @Schema(description = "카테고리 ID", example = "1")
        private Long categoryIdx;

        @Schema(description = "카테고리 이름", example = "자유 게시판")
        private String categoryName;
    }

    @Getter
    @Schema(description = "카테고리 생성 요청")
    public static class CategoryRegisterRequest {
        @Schema(description = "카테고리 이름", example = "햄스터 게시판")
        private String categoryName;
    }

    @Getter
    @Schema(description = "Q&A 질문 등록 요청")
    public static class RegisterQuestionRequest {
        @Schema(description = "질문 내용", example = "강아지 사료 추천 좀요")
        private String question;

        @Schema(description = "작성일", example = "2025-04-07")
        private String createAt;
    }

    @Getter
    @Schema(description = "Q&A 답변 등록 요청")
    public static class RegisterAnswerRequest {
        @Schema(description = "답변 내용", example = "오리 고기 사료 괜찮아요")
        private String answer;

        @Schema(description = "작성일", example = "2025-04-07")
        private String createAt;
    }
}