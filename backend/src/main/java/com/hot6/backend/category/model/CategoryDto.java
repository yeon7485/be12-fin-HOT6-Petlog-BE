package com.hot6.backend.category.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

public class CategoryDto {

    @Getter
    public class CategoryCreateRequest {
        @Schema(description = "카테고리 이름", example = "병원")
        private String categoryName;

        @Schema(description = "카테고리 색상 코드", example = "#FF8A65")
        private String color; // 선택일 수 있음

        @Schema(description = "카테고리 설명", example = "정기 검진 및 예방접종을 위한 병원 방문 일정")
        private String description;

        @Schema(description = "카테고리 종류", example = "일정, 기록 중 하나의 카테고리로 설정 가능")
        private String type;
    }

    @Getter
    public class CategoryUpdateRequest {
        @Schema(description = "수정할 카테고리의 ID", example = "1")
        private Long categoryId;

        @Schema(description = "카테고리 이름", example = "병원")
        private String categoryName;

        @Schema(description = "카테고리 색상 코드", example = "#FF8A65")
        private String color;

        @Schema(description = "카테고리 설명", example = "정기 검진 및 예방접종을 위한 병원 방문 일정")
        private String description;
    }

    @Getter
    @Builder
    public static class CategoryResponse{
        @Schema(description = "카테고리 고유 ID", example = "1")
        private Long categoryIdx;

        @Schema(description = "카테고리 이름", example = "병원")
        private String categoryName;

        @Schema(description = "카테고리 색상 코드", example = "#FF8A65")
        private String color;
    }
}
