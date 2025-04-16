package com.hot6.backend.pet.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
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
        private Boolean isNeutering;

        @Schema(description = "특이사항", example = "치아가 안좋음")
        private String specificInformation;

        @Schema(description = "상태", example = "정상")
        private String status;
    }
    @Data
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
        private Boolean isNeutering;

        @Schema(description = "특이사항", example = "겁이 많음")
        private String specificInformation;

        @Schema(description = "프로필 이미지 URL", example = "https://example.com/coco.jpg")
        private String profileImageUrl;
        private Long userId;
    }

    @Getter
    @Schema(description = "반려동물 카드 수정 요청")
    public static class PetCardUpdateRequest {
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

        @Schema(description = "중성화 여부", example = "true")
        private boolean isNeutering;

        @Schema(description = "특이사항", example = "성격 더러움, 꼬리가 잘려있음")
        private String specificInformation;

        @Schema(description = "상태", example = "정상")
        private String status;

        // ProfileImageUrl 필드를 주석 처리
        // @Schema(description = "프로필 이미지 URL", example = "https://example.com/coco-updated.jpg")
        // private String profileImageUrl;

        // Pet 엔티티로 변환하는 메소드
        public Pet toEntity(PetCardUpdateRequest petCardUpdateRequest) {
            Pet pet = new Pet();
            pet.setIdx(this.id);
            pet.setName(this.name);
            pet.setBreed(this.breed);
            pet.setGender(this.gender);
            pet.setBirthDate(this.birthDate);
            pet.setNeutering(this.isNeutering);
            pet.setSpecificInformation(this.specificInformation);
            pet.setStatus(PetStatus.valueOf(this.status));
            // profileImageUrl 설정 제거
            // pet.setProfileImageUrl(this.profileImageUrl);
            return pet;
        }
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

        private Boolean isNeutering;

        private String specificInformation;

        // `from` 메서드 추가
        public static PetCardDetailResponse from(Pet pet) {
            return PetCardDetailResponse.builder()
                    .id(pet.getIdx())
                    .name(pet.getName())
                    .breed(pet.getBreed())
                    .gender(pet.getGender())
                    .birthDate(pet.getBirthDate())
                    .profileImageUrl(pet.getProfileImageUrl())
                    .isNeutering(pet.isNeutering())
                    .specificInformation(pet.getSpecificInformation())
                    .build();
        }
    }
}
