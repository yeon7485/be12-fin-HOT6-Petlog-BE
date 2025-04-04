package com.hot6.backend.pet;

import com.hot6.backend.pet.model.PetDto;
import com.hot6.backend.schedule.model.ScheduleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetController {

    // 반려동물 목록 조회 (해당 사용자 ID 기준)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PetDto.PetCard>> getPetList(@PathVariable Long userId) {
        List<PetDto.PetCard> pets = new ArrayList<>();
        // Mock 데이터 예시
        pets.add(PetDto.PetCard.builder()
                .idx(1L)
                .name("Coco")
                .breed("Poodle")
                .gender("Female")
                .birthDate("2022-01-01")
                .profileImageUrl("https://example.com/coco.jpg")
                .build());

        return ResponseEntity.ok(pets);
    }

    // 반려동물 상세 조회
    @GetMapping("/{petId}")
    public ResponseEntity<PetDto.PetCardDetailResponse> getPetDetail(@PathVariable Long petId) {
        PetDto.PetCardDetailResponse detail = PetDto.PetCardDetailResponse.builder()
                .id(petId)
                .name("Coco")
                .breed("Poodle")
                .gender("Female")
                .birthDate("2022-01-01")
                .profileImageUrl("https://example.com/coco.jpg")
                .schedules(ScheduleDto.MonthlyScheduleResponse.builder()
                        .schedule(List.of(ScheduleDto.Info.builder()
                                .title("병원")
                                .day(15)
                                .build(),
                                        ScheduleDto.Info.builder()
                                                .title("미용실")
                                                .day(16)
                                                .build())

                                )
                        .build()) // 예시: 일정 없음
                .build();

        return ResponseEntity.ok(detail);
    }

    // 반려동물 카드 생성
    @PostMapping
    public ResponseEntity<String> createPet(@RequestBody PetDto.PetCardCreateRequest request) {
        // 저장 로직...
        return ResponseEntity.ok("반려동물 카드가 생성되었습니다.");
    }

    // 반려동물 카드 수정
    @PutMapping("/{petId}")
    public ResponseEntity<String> updatePet(@PathVariable Long petId,
                                            @RequestBody PetDto.PetCardUpdateRequest request) {
        // 수정 로직...
        return ResponseEntity.ok("반려동물 카드가 수정되었습니다.");
    }

    // 반려동물 월간 일정 조회
    @GetMapping("/{petIdx}/schedule")
    public ResponseEntity<ScheduleDto.MonthlyScheduleResponse> getMonthlySchedule(@PathVariable Long petIdx,
                                                                                @RequestParam int month,
                                                                                @RequestParam int year) {
        ScheduleDto.MonthlyScheduleResponse monthlySchedule = ScheduleDto.MonthlyScheduleResponse.builder()
                .schedule(List.of( ScheduleDto.Info.builder()
                        .title("미용 예약")
                        .build(),ScheduleDto.Info.builder()
                        .title("병원 예약")
                        .build())).build();

        return ResponseEntity.ok(monthlySchedule);
    }

}
