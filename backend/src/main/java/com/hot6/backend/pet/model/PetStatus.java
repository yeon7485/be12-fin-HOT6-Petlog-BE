package com.hot6.backend.pet.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum PetStatus {
    정상,
    실종,
    파양,
    사망;
}