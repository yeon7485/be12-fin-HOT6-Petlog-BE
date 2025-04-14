package com.hot6.backend.pet.model;

import com.hot6.backend.schedule.model.ScheduleDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

public class PetDto {

    @Getter
    @Builder
    @Schema(description = "반려동물 카드 정보")
    public static class PetCard {
        @Schema(description = "반려동물 ID", example = "1")
        private Long idx;

        @Schema(description = "이름", example = "Coco")
        private String name;

        @Schema(description = "품종", example = "Poodle")
        private String breed;

        @Schema(description = "성별", example = "Female")
        private String gender;

        @Schema(description = "생일", example = "2022-01-01")
        private String birthDate;

        @Schema(description = "프로필 이미지 URL", example = "https://example.com/coco.jpg")
        private String profileImageUrl;

        @Schema(description = "중성화 여부", example = "true")
        private boolean isNeutering;

        @Schema(description = "특이사항", example = "치아가 안좋음")
        private String specificInformation;

        @Schema(description = "상태", example = "정상")
        private String status;
    }
    @Getter
    @Schema(description = "반려동물 카드 생성 요청")
    public static class PetCardCreateRequest {
        @Schema(description = "이름", example = "Coco")
        private String name;

        @Schema(description = "품종", example = "Poodle")
        private String breed;

        @Schema(description = "성별", example = "Female")
        private String gender;

        @Schema(description = "생일", example = "2022-01-01")
        private String birthDate;

        @Schema(description = "중성화 여부", example = "true")
        private boolean isNeutering;

        @Schema(description = "특이사항", example = "겁이 많음")
        private String specificInformation;

        @Schema(description = "프로필 이미지 URL", example = "https://example.com/coco.jpg")
        private String profileImageUrl;
    }

    @Getter
    @Schema(description = "반려동물 카드 수정 요청")
    public static class PetCardUpdateRequest {
        @Schema(description = "이름", example = "Coco")
        private String name;

        @Schema(description = "품종", example = "Poodle")
        private String breed;

        @Schema(description = "성별", example = "Female")
        private String gender;

        @Schema(description = "생일", example = "2022-01-01")
        private String birthDate;

        @Schema(description = "프로필 이미지 URL", example = "https://example.com/coco-updated.jpg")
        private String profileImageUrl;
    }

    @Getter
    @Builder
    @Schema(description = "반려동물 카드 상세 응답")
    public static class PetCardDetailResponse {
        @Schema(description = "반려동물 ID", example = "1")
        private Long id;

        @Schema(description = "이름", example = "Coco")
        private String name;

        @Schema(description = "품종", example = "Poodle")
        private String breed;

        @Schema(description = "성별", example = "Female")
        private String gender;

        @Schema(description = "생일", example = "2022-01-01")
        private String birthDate;

        @Schema(description = "프로필 이미지 URL", example = "https://example.com/coco.jpg")
        private String profileImageUrl;

        @Schema(description = "월간 일정 응답 정보")
        private ScheduleDto.MonthlyScheduleResponse schedules;
    }

    @Getter
    @Schema(description = "반려동물 카드 공유 요청")
    public static class PetCardShareRequest {
        @Schema(description = "공유할 반려동물 ID", example = "1")
        private Long petId;

        @Schema(description = "공유 대상 타입 (chat | post)", example = "chat")
        private String targetType;

        @Schema(description = "공유 대상 ID (채팅방 ID 또는 게시글 ID)", example = "123")
        private Long targetId;
    }
}
