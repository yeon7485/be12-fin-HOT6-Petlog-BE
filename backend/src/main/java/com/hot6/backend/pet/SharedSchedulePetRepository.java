package com.hot6.backend.pet;

import com.hot6.backend.pet.model.SharedSchedulePet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SharedSchedulePetRepository extends JpaRepository<SharedSchedulePet, Long> {
    Optional<SharedSchedulePet> findByPet_IdxAndSchedule_Idx(Long petIdx, Long scheduleIdx);
}
