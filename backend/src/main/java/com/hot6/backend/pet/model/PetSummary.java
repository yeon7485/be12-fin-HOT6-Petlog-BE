package com.hot6.backend.pet.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PetSummary {
    private Long idx;
    private String name;
    private String breed;
    private String profileImageUrl;
    private String gender;
    private String birthDate;

    public static PetSummary from(Pet pet) {
        return PetSummary.builder()
                .idx(pet.getIdx())
                .name(pet.getName())
                .breed(pet.getBreed())
                .profileImageUrl(pet.getProfileImageUrl())
                .gender(pet.getGender())
                .birthDate(pet.getBirthDate() != null ? pet.getBirthDate().toString() : null)
                .build();
    }
}
