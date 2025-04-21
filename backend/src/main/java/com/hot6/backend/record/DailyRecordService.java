package com.hot6.backend.record;

import com.hot6.backend.category.model.Category;
import com.hot6.backend.category.model.CategoryRepository;
import com.hot6.backend.common.BaseResponseStatus;
import com.hot6.backend.common.exception.BaseException;
import com.hot6.backend.pet.PetRepository;
import com.hot6.backend.pet.PetService;
import com.hot6.backend.pet.model.Pet;
import com.hot6.backend.record.model.DailyRecord;
import com.hot6.backend.record.model.DailyRecordDto;
import com.hot6.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.hot6.backend.schedule.model.QSchedule.schedule;

@Service
@RequiredArgsConstructor
public class DailyRecordService {
    private final DailyRecordRepository dailyRecordRepository;
    private final PetRepository petRepository;
    private final CategoryRepository categoryRepository;

    public void createDailyRecord(Long petIdx, DailyRecordDto.RecordCreateRequest dto) {
        Pet pet = petRepository.findById(petIdx).orElseThrow(() -> new BaseException(BaseResponseStatus.PET_NOT_FOUND));

        dailyRecordRepository.save(dto.toEntity(pet));
    }

    public List<DailyRecordDto.SimpleDailyRecord> getRecordsByDate(Integer year, Integer month, Integer day) {
        //Pet pet = petRepository.findById(petIdx).orElseThrow(() -> new BaseException(BaseResponseStatus.PET_NOT_FOUND));

        LocalDate date = LocalDate.of(year, month, day);
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX); // 23:59:59.999999999

        List<DailyRecord> records = dailyRecordRepository.findAllByDateBetween(start, end);

        List<DailyRecordDto.SimpleDailyRecord> recordList = new ArrayList<>();

        for(DailyRecord record : records) {
            Category category = Category.builder()
                    .name("체중")
                    .color("#00C9CD")
                    .idx(record.getCategoryIdx())
                    .build();

            recordList.add(DailyRecordDto.SimpleDailyRecord.from(record, category));

        }
        return recordList;
    }
    }


