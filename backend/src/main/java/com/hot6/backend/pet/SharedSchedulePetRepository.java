package com.hot6.backend.pet;

import com.hot6.backend.pet.model.SharedSchedulePet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SharedSchedulePetRepository extends JpaRepository<SharedSchedulePet, Long> {
}
