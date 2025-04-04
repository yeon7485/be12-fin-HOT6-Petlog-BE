package com.hot6.backend.pet.model;

import com.hot6.backend.schedule.model.ScheduleDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PetDto {

    @Getter
    @Builder
    public static class PetCard {
        private Long idx;
        private String name;
        private String breed;
        private String gender;
        private String birthDate;
        private String profileImageUrl;
        private boolean isNeutering;
        private String specificInformation;
    }

    @Getter
    public static class PetCardCreateRequest {
        private String name;
        private String breed;
        private String gender;
        private String birthDate;
        private boolean isNeutering;
        private String specificInformation;
        private String profileImageUrl;
    }

    @Getter
    public static class PetCardUpdateRequest {
        private String name;
        private String breed;
        private String gender;
        private String birthDate;
        private String profileImageUrl;
    }

    @Getter
    @Builder
    public static class PetCardDetailResponse {
        private Long id;
        private String name;
        private String breed;
        private String gender;
        private String birthDate;
        private String profileImageUrl;
        private ScheduleDto.MonthlyScheduleResponse schedules; // 월간 일정
    }

}


