package com.hot6.backend.pet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum PetStatus {
    @JsonProperty("사망")
    DECEASED,

    @JsonProperty("실종")
    LOST,

    @JsonProperty("정상")
    HEALTHY,

    @JsonProperty("파양")
    ABANDONED
}