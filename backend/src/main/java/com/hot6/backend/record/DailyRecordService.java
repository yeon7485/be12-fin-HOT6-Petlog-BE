package com.hot6.backend.record;

import com.hot6.backend.common.BaseResponseStatus;
import com.hot6.backend.common.exception.BaseException;
import com.hot6.backend.pet.PetRepository;
import com.hot6.backend.pet.PetService;
import com.hot6.backend.pet.model.Pet;
import com.hot6.backend.record.model.DailyRecordDto;
import com.hot6.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DailyRecordService {
    private final DailyRecordRepository dailyRecordRepository;
    private final PetRepository petRepository;

    public void createDailyRecord(Long petIdx, DailyRecordDto.RecordCreateRequest dto) {
        Pet pet = petRepository.findById(petIdx).orElseThrow(() -> new BaseException(BaseResponseStatus.PET_NOT_FOUND));

        dailyRecordRepository.save(dto.toEntity(pet));
    }
}
