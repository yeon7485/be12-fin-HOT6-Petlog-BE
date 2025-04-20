package com.hot6.backend.record.model;

import com.hot6.backend.common.BaseEntity;
import com.hot6.backend.pet.model.Pet;
import com.hot6.backend.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
public class DailyRecord extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String rTitle;
    private String rMemo;
    private LocalDateTime date;

    private String imageUrl;
    private Long categoryIdx;

    @ManyToOne
    @JoinColumn(name = "pet_idx")
    private Pet pet;
}
