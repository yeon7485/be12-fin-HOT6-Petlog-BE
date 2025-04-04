package com.hot6.backend.board.model;

import com.hot6.backend.board.comment.model.CommentDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class BoardDto {

    @Getter
    public static class CategoryList{
        private Long categoryIdx;
        private String category;
    }

    @Getter
    public static class PostCreateRequest {
        private String title;
        private String content;
    }

    @Getter
    public static class BoardUpdateRequest {
        private String title;
        private String content;
    }



    @Getter
    @Builder
    public static class BoardDetailResponse {
        private Long idx;
        private String title;
        private String content;
        private String authorName;
        private String createdAt;
        private List<CommentDto.CommentElement> comments;
    }


    @Getter
    @Builder
    public static class CategoryResponse {
        private Long categoryIdx;
        private String categoryName;
    }

    @Getter
    @Builder
    public static class BoardInfo{
        private Long idx;
        private String title;
        private String authorName;
        private String createdAt;
    }

    @Getter
    public class CategoryRegisterRequest {
        private String categoryName;
    }


    @Getter
    public class RegisterQuestionRequest{
        private String question;
        private String createAt;
    }

    public class RegisterAnswerRequest {
        private String answer;
        private String createAt;
    }
}
