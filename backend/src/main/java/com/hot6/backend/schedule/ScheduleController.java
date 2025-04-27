package com.hot6.backend.schedule;

import com.hot6.backend.common.BaseResponse;
import com.hot6.backend.common.BaseResponseStatus;
import com.hot6.backend.schedule.model.ScheduleDto;
import com.hot6.backend.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/schedule")
@Tag(name = "Schedule", description = "일정 관리 API")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @Operation(summary = "사용자의 전체 월간 일정 조회", description = "사용자의 모든 반려동물 월간 일정을 조회합니다.")
    @GetMapping("/pet")
    public ResponseEntity<BaseResponse<List<ScheduleDto.SimpleSchedule>>> getSchedules(@AuthenticationPrincipal User user) {
        List<ScheduleDto.SimpleSchedule> list = scheduleService.getAllSchedule(user.getIdx());

        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS, list));
    }

    @Operation(summary = "반려동물별 월간 일정 조회", description = "특정 반려동물의 월간 일정을 조회합니다.")
    @GetMapping("/pet/{petIdx}")
    public ResponseEntity<BaseResponse<List<ScheduleDto.SimpleSchedule>>> getSchedulesByPet(
            @PathVariable Long petIdx) {

        List<ScheduleDto.SimpleSchedule> list = scheduleService.getSchedulesByPet(petIdx);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS, list));
    }

    @Operation(summary = "날짜별 일정 전체 조회", description = "특정 날짜의 반려동물 전체 일정을 조회합니다.")
    @GetMapping("/date/{year}/{month}/{day}")
    public ResponseEntity<BaseResponse<List<ScheduleDto.SimpleSchedule>>> getAllSchedulesByDate(
            @PathVariable Integer year,
            @PathVariable Integer month,
            @PathVariable Integer day,
            @AuthenticationPrincipal User user
    ) {
        List<ScheduleDto.SimpleSchedule> list = scheduleService.getSchedulesByDate(user.getIdx(), year, month, day);

        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS, list));
    }

    @Operation(summary = "특정 반려동물의 날짜별 일정 조회", description = "특정 날짜의 특정 반려동물 일정을 조회합니다.")
    @GetMapping("/pet/{petIdx}/date/{year}/{month}/{day}")
    public ResponseEntity<BaseResponse<List<ScheduleDto.SimpleSchedule>>> getSchedulesByPetAndDate(
            @PathVariable Long petIdx,
            @PathVariable Integer year,
            @PathVariable Integer month,
            @PathVariable Integer day
    ) {
        List<ScheduleDto.SimpleSchedule> list = scheduleService.getSchedulesByPetAndDate(petIdx, year, month, day);

        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS, list));
    }

    @Operation(summary = "일정 생성", description = "선택한 카테고리와 반려동물에 대해 일정을 생성합니다.")
    @PostMapping("/pet/{petIdx}")
    public ResponseEntity<BaseResponse<String>> createSchedule(@RequestBody ScheduleDto.ScheduleCreateRequest request,
                                                               @PathVariable Long petIdx
    ) {
        scheduleService.createSchedule(petIdx, request);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS));
    }

    @Operation(summary = "일정 상세 조회", description = "특정 일정의 상세 정보를 조회합니다.")
    @GetMapping("/{scheduleIdx}")
    public ResponseEntity<BaseResponse<ScheduleDto.ScheduleDetail>> getScheduleDetail(@PathVariable Long scheduleIdx) {

        ScheduleDto.ScheduleDetail scheduleDetail = scheduleService.getScheduleDetail(scheduleIdx);

        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS, scheduleDetail));
    }

    @Operation(summary = "일정 수정", description = "선택한 카테고리와 반려동물의 일정을 수정합니다.")
    @PutMapping("/pet/{petIdx}/schedule/{scheduleIdx}")
    public ResponseEntity<String> updateSchedule(
            @AuthenticationPrincipal User user,
            @PathVariable Long scheduleIdx,
            @PathVariable Long petIdx,
            @RequestBody ScheduleDto.ScheduleUpdateRequest scheduleUpdateRequest) {
        scheduleService.updatePetSchedule(user,petIdx,scheduleIdx,scheduleUpdateRequest);
        return ResponseEntity.ok("ok");
    }

    @Operation(summary = "일정 삭제", description = "선택한 카테고리와 반려동물의 일정을 삭제합니다.")
    @DeleteMapping("/pet/{petIdx}/schedule/{scheduleIdx}")
    public ResponseEntity<String> deleteSchedule(
            @AuthenticationPrincipal User user,
            @PathVariable Long scheduleIdx,
            @PathVariable Long petIdx) {
        scheduleService.deletePetSchedule(petIdx,scheduleIdx);
        return ResponseEntity.ok("ok");
    }


    @Operation(summary = "일정 공유", description = "사용자가 본인의 일정을 사용자나 그룹 채팅방에 공유합니다.")
    @PostMapping("/share")
    public ResponseEntity<String> shareSchedule(@RequestBody ScheduleDto.ScheduleShareRequest request) {
        return ResponseEntity.ok("일정 공유 완료");
    }

}
