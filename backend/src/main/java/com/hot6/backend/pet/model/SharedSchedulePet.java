package com.hot6.backend.pet.model;

import com.hot6.backend.schedule.model.Schedule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SharedSchedulePet {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_idx")
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_idx")
    private Pet pet;
}
