package com.hot6.backend.record;

import com.hot6.backend.category.model.Category;
import com.hot6.backend.category.model.CategoryRepository;
import com.hot6.backend.common.BaseResponseStatus;
import com.hot6.backend.common.exception.BaseException;
import com.hot6.backend.pet.PetRepository;
import com.hot6.backend.pet.model.Pet;
import com.hot6.backend.record.model.DailyRecord;
import com.hot6.backend.record.model.DailyRecordDto;
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

    public List<DailyRecordDto.SimpleDailyRecord> getRecordsByDate(Long userIdx, Integer year, Integer month, Integer day) {

        LocalDate date = LocalDate.of(year, month, day);
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX); // 23:59:59.999999999

        List<DailyRecordDto.SimpleDailyRecord> recordList = new ArrayList<>();
        List<Pet> pets = petRepository.findByUserIdx(userIdx);

        for (Pet pet : pets) {
            List<DailyRecord> records = dailyRecordRepository.findAllByPetAndDateBetween(pet, start, end);

            for (DailyRecord record : records) {
                System.out.println(record);
                Category category = categoryRepository.findById(record.getCategoryIdx())
                        .orElseThrow(() -> new BaseException(BaseResponseStatus.CATEGORY_NOT_FOUND));
                recordList.add(DailyRecordDto.SimpleDailyRecord.from(record, category));
            }
        }
        return recordList;
    }

    public List<DailyRecordDto.SimpleDailyRecord> getRecordsByPetAndDate(Long petIdx, Integer year, Integer month, Integer day) {
        LocalDate date = LocalDate.of(year, month, day);
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX); // 23:59:59.999999999

        List<DailyRecordDto.SimpleDailyRecord> recordList = new ArrayList<>();
        Pet pet = petRepository.findById(petIdx).orElseThrow(() ->
                new BaseException(BaseResponseStatus.PET_NOT_FOUND));

        List<DailyRecord> records = dailyRecordRepository.findAllByPetAndDateBetween(pet, start, end);

        for (DailyRecord record : records) {
            System.out.println(record);
            Category category = categoryRepository.findById(record.getCategoryIdx())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.CATEGORY_NOT_FOUND));
            recordList.add(DailyRecordDto.SimpleDailyRecord.from(record, category));
        }

        return recordList;
    }

    public DailyRecordDto.RecordDetail getRecordDetail(Long recordIdx) {
        DailyRecord record = dailyRecordRepository.findById(recordIdx).orElseThrow(
                () -> new BaseException(BaseResponseStatus.DAILY_RECORD_NOT_FOUND));

        Category category = categoryRepository.findById(record.getCategoryIdx())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.CATEGORY_NOT_FOUND));

        return DailyRecordDto.RecordDetail.from(record, category);
    }
}


