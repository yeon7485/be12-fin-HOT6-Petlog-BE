package com.hot6.backend.pet;

import com.hot6.backend.pet.model.PetDto;
import com.hot6.backend.schedule.model.ScheduleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/pet")
@Tag(name = "Pet", description = "반려동물 기능 API")
public class PetController {
    private final PetService petService;

    // 반려동물 목록 조회 (해당 사용자 ID 기준)
    @Operation(summary = "사용자의 반려동물 카드 목록 조회", description = "사용자가 등록한 반려동물 카드 목록을 조회합니다.")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PetDto.PetCard>> getPetList(@PathVariable Long userId) {
        List<PetDto.PetCard> pets = petService.getPetCardsByUserId(userId);
        return ResponseEntity.ok(pets);
    }

    // 반려동물 상세 조회
    @Operation(summary = "반려동물 카드 상세 조회", description = "특정 반려동물 카드의 상세 정보를 조회합니다.")
    @GetMapping("/{petId}")
    public ResponseEntity<PetDto.PetCardDetailResponse> getPetDetail(@PathVariable Long petId) {
        PetDto.PetCardDetailResponse detail = petService.getPetDetailById(petId);
        return ResponseEntity.ok(detail);
    }

    // 반려동물 카드 생성
    @PostMapping("/create")
    public ResponseEntity<String> createPetCard(
            @RequestPart("pet") PetDto.PetCardCreateRequest request,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
        String imagePath = null;
        if (profileImage != null && !profileImage.isEmpty()) {
            imagePath = petService.saveProfileImage(profileImage);
        }

        petService.createPetCard(request, imagePath);
        return ResponseEntity.ok("반려동물 카드가 생성되었습니다.");
    }

    // 반려동물 카드 수정
    @Operation(summary = "반려동물 카드 수정", description = "기존 반려동물 카드 정보를 수정합니다.")
    @PutMapping("/{petId}")
    public ResponseEntity<String> updatePet(@PathVariable Long petId,
                                            @RequestBody PetDto.PetCardUpdateRequest request) {
        // 수정 로직...
        return ResponseEntity.ok("반려동물 카드가 수정되었습니다.");
    }

    @Operation(summary = "반려동물 카드 삭제", description = "사용자의 반려동물 카드를 삭제합니다.")
    @DeleteMapping("/{petId}")
    public ResponseEntity<String> deletePet(@PathVariable Long petId) {
        return ResponseEntity.ok("반려동물 카드가 삭제되었습니다.");
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
