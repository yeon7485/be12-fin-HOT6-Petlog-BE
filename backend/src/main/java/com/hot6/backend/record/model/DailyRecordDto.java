package com.hot6.backend.record.model;

import com.hot6.backend.category.model.Category;
import com.hot6.backend.pet.model.Pet;
import com.hot6.backend.schedule.model.Schedule;
import com.hot6.backend.schedule.model.ScheduleDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class DailyRecordDto {
    private static final String PHOTO_CATEGORY = "오늘의 사진";


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
                    .categoryIdx(categoryIdx)
                    .pet(pet)
                    .build();
        }
    }

    @Getter
    @Builder
    @Schema(description = "날짜별 기록 응답 DTO")
    public static class SimpleDailyRecord {
        @Schema(description = "기록 idx", example = "1")
        private Long idx;

        @Schema(description = "기록 제목", example = "산책")
        private String title;

        @Schema(description = "기록 메모", example = "메모")
        private String memo;

        @Schema(description = "오늘의 사진 이미지 URL", example = "image.png")
        private String imageUrl;

        @Schema(description = "기록 날짜 (yyyy-MM-dd 형식)", example = "2025-04-02")
        private LocalDateTime date;

        @Schema(description = "카테고리 색상", example = "#000000")
        private String color;

        @Schema(description = "카테고리 이름", example = "병원")
        private String categoryName;

        @Schema(description = "펫 이름", example = "콩이")
        private String petName;


        public static SimpleDailyRecord from(DailyRecord dailyRecord, Category category) {
            String imageUrl = PHOTO_CATEGORY.equals(category.getName()) ? dailyRecord.getImageUrl() : null;
            return SimpleDailyRecord.builder()
                    .idx(dailyRecord.getIdx())
                    .title(dailyRecord.getRTitle())
                    .memo(dailyRecord.getRMemo())
                    .imageUrl(imageUrl)
                    .date(dailyRecord.getDate())
                    .color(category.getColor())
                    .categoryName(category.getName())
                    .petName(dailyRecord.getPet().getName())
                    .build();
        }
    }




}
