package com.hot6.backend.pet.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String name;
    private String breed;
    private String gender;
    private String birthDate;
    private String profileImageUrl;
    private boolean isNeutering;
    private String specificInformation;
    @Enumerated(EnumType.STRING)
    private PetStatus status;

    @Column(nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PetImage> petImageList = new ArrayList<>();
}
