package com.hot6.backend.pet;

import com.hot6.backend.pet.model.Pet;
import com.hot6.backend.pet.model.SharedSchedulePet;
import com.hot6.backend.schedule.model.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SharedSchedulePetService {
    private final SharedSchedulePetRepository sharedSchedulePetRepository;
    private final PetService petService;

    @Transactional
    public void saveAll(List<Long> petIds, Schedule schedule) {
        for(Long petId : petIds) {
            Pet pet = petService.findById(petId);

            sharedSchedulePetRepository.save(SharedSchedulePet.builder()
                            .pet(pet)
                    .schedule(schedule)
                    .build());
        }
    }
}
