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

import java.util.ArrayList;
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

        return ResponseEntity.ok(new BaseResponse<>(list));
    }

    @Operation(summary = "반려동물별 월간 일정 조회", description = "특정 반려동물의 월간 일정을 조회합니다.")
    @GetMapping("/pet/{petIdx}")
    public ResponseEntity<String> getSchedule(@PathVariable Long petIdx,
                                                                           @RequestParam int month) {

        return ResponseEntity.ok("");
    }

    @Operation(summary = "일정 상세 조회", description = "반려동물의 하루의 상세 일정을 조회합니다.")
    @GetMapping("/pet/{petIdx}/day")
    public ResponseEntity<ScheduleDto.ScheduleDetail> getScheduleDetail(@PathVariable Long petIdx,
                                                                        @RequestParam int day) {
        return ResponseEntity.ok(ScheduleDto.ScheduleDetail.builder()
                .idx(1L)
                .title("일정")
                .content("해야할 일")
                .time("2025-03-31")
                .isCompleted(true)
                .build());
    }


    @Operation(summary = "일정 생성", description = "선택한 카테고리와 반려동물에 대해 일정을 생성합니다.")
    //category idx 는 url 로 전달하기 보다는 화면에 있는 컴포넌트를 통해서
    @PostMapping("/pet/{petIdx}")
    public ResponseEntity<BaseResponse<String>> createSchedule(@RequestBody ScheduleDto.ScheduleCreateRequest request,
                                                       @PathVariable Long petIdx,
                                                       @AuthenticationPrincipal User user) {
        scheduleService.createSchedule(user, petIdx, request);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS));
    }

    @Operation(summary = "일정 수정", description = "선택한 카테고리와 반려동물의 일정을 수정합니다.")
    @PutMapping("/pet/{petIdx}")
    public ResponseEntity<String> updateSchedule(@PathVariable Long petIdx,
                                                 @RequestBody ScheduleDto.ScheduleUpdateRequest scheduleUpdateRequest) {
        return ResponseEntity.ok("ok");
    }

    @Operation(summary = "일정 삭제", description = "선택한 카테고리와 반려동물의 일정을 삭제합니다.")
    @DeleteMapping("/pet/{petIdx}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Long petIdx) {
        return ResponseEntity.ok("ok");
    }


    @Operation(summary = "일정 공유", description = "사용자가 본인의 일정을 사용자나 그룹 채팅방에 공유합니다.")
    @PostMapping("/share")
    public ResponseEntity<String> shareSchedule(@RequestBody ScheduleDto.ScheduleShareRequest request) {
        return ResponseEntity.ok("일정 공유 완료");
    }

}
