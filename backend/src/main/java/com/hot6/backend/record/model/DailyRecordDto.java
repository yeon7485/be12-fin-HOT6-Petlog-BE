package com.hot6.backend.record.model;

import com.hot6.backend.pet.model.Pet;
import com.hot6.backend.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class DailyRecordDto {

    @Getter
    @Builder
    @Schema(description = "기록 상세 응답 DTO")
    public static class RecordDetail {
        @Schema(description = "기록 ID", example = "1")
        private Long recordId;

        @Schema(description = "기록 제목", example = "체중")
        private String title;

        @Schema(description = "기록 메모", example = "4.2kg")
        private String memo;

        @Schema(description = "기록 카테고리", example = "체중")
        private String category;

        @Schema(description = "기록 날짜", example = "2025-04-07")
        private String date;
    }

    @Getter
    @Schema(description = "기록 생성 요청 DTO")
    public static class RecordCreateRequest {
        @Schema(description = "기록 제목", example = "산책 메모")
        private String title;

        @Schema(description = "기록 상세 메모", example = "30분 동안 공원 산책을 했어요.")
        private String memo;

        @Schema(description = "카테고리", example = "walk", allowableValues = {"walk", "hospital", "medication", "etc"})
        private Long categoryIdx;

        @Schema(description = "기록 날짜 (yyyy-MM-dd 형식)", example = "2025-04-02")
        private LocalDateTime date;

        @Schema(description = "이미지 URL", example = "testImg.png")
        private String imageUrl;

        public DailyRecord toEntity(Pet pet) {
            return DailyRecord.builder()
                    .rTitle(title)
                    .rMemo(memo)
                    .date(date)
                    .imageUrl(imageUrl)
                    .pet(pet)
                    .build();
        }


    }


}
