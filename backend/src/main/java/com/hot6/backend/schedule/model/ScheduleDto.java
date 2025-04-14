package com.hot6.backend.schedule.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


public class ScheduleDto {
    @Getter
    @Builder
    @Schema(description = "월간 일정 응답 DTO")
    public static class MonthlyScheduleResponse {
        @Schema(description = "월간 일정 리스트", implementation = Info.class)
        private List<ScheduleDto.Info> schedule;
    }

    @Getter
    @Builder
    @Schema(description = "일정 정보 요약 DTO")
    public static class Info {
        @Schema(description = "일정 제목", example = "병원 방문")
        private String title;

        @Schema(description = "일정 날짜 (일)", example = "15")
        private int day;
    }

    @Getter  @Builder
    public static class ScheduleDetail {
        @Schema(description = "일정 고유 ID", example = "101")
        private Long idx;
        @Schema(description = "일정 제목", example = "예방접종")
        private String title;
        @Schema(description = "일정 내용", example = "병원에서 강아지 예방접종 받기")
        private String content;
        @Schema(description = "시간 (HH:mm 형식)", example = "10:30")
        private String time; // HH:mm
        @Schema(description = "일정 완료 여부", example = "false")
        private boolean isCompleted;
        @Schema(description = "일정 반복 여부", example = "true")
        private boolean isRepeat;

        @Schema(description = "반복 주기", example = "weekly")
        private String repeatCycle;
    }

    @Getter
    @Schema(description = "일정 생성 요청 DTO")
    public static class ScheduleCreateRequest {
        @Schema(description = "일정 제목", example = "예방접종")
        private String title;

        @Schema(description = "장소", example = "병원")
        private String location;

        @Schema(description = "메모", example = "병원에서 예방접종")
        private String memo;

        @Schema(description = "일정 날짜", example = "2025-04-01")
        private String date;

        @Schema(description = "카테고리", example = "병원")
        private String category;

        @Schema(description = "일정 시작 시간", example = "10:00")
        private String startTime;

        @Schema(description = "일정 종료 시간", example = "11:00")
        private String endTime;

        @Schema(description = "반복 여부", example = "true")
        private boolean isRepeat;

        @Schema(description = "반복 주기", example = "일주일")
        private String repeatCycle;
    }

    @Getter
    @Schema(description = "일정 수정 요청 DTO")
    public static class ScheduleUpdateRequest {
        @Schema(description = "일정 제목", example = "예방접종")
        private String title;

        @Schema(description = "장소", example = "병원")
        private String location;

        @Schema(description = "메모", example = "병원에서 예방접종")
        private String memo;

        @Schema(description = "기록 날짜 (yyyy-MM-dd 형식)", example = "2025-04-02")
        private String date;

        @Schema(description = "카테고리", example = "hospital", allowableValues = {"walk", "hospital", "medication", "etc"})
        private String category;

        @Schema(description = "일정 시작 시간", example = "10:00")
        private String startTime;

        @Schema(description = "일정 종료 시간", example = "11:00")
        private String endTime;
    }


    @Schema(description = "일정 공유 요청 DTO")
    @Getter
    public static class ScheduleShareRequest {
        @Schema(description = "공유할 일정 ID", example = "101")
        private Long scheduleId;

        @Schema(description = "공유 대상 타입 (user | group)", example = "user")
        private String targetType;

        @Schema(description = "공유 대상 ID (사용자 ID 또는 그룹 ID)", example = "55")
        private Long targetId;
    }

}
