package com.hot6.backend.daily;

import com.hot6.backend.daily.model.DailyRecordDto;
import com.hot6.backend.schedule.model.ScheduleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/daily-record")
@Tag(name = "daily-record", description = "기록(체중, 수면 등) 관리 API")
public class DailyRecordController {

    @Operation(summary = "기록 상세 조회", description = "하루의 기록을 카테고리별로 조회합니다.")
    @GetMapping("/pet/{petIdx}/record/{recordIdx}")
    public ResponseEntity<List<DailyRecordDto.RecordDetail>> getRecordByDate(
            @PathVariable Long petIdx,
            @PathVariable Long recordIdx,
            @RequestParam String date // yyyy-MM-dd
    ) {
        List<DailyRecordDto.RecordDetail> records = List.of(
                DailyRecordDto.RecordDetail.builder()
                        .recordId(1L)
                        .title("체중 측정")
                        .memo("4.3kg")
                        .category("체중")
                        .date(date)
                        .build()
        );
        return ResponseEntity.ok(records);
    }

    @Operation(summary = "기록 수정", description = "기존의 기록을 수정합니다.")
    @PutMapping("/{recordIdx}")
    public ResponseEntity<String> updateRecord(
            @PathVariable Long recordIdx,
            @RequestBody DailyRecordDto.RegisterDailyRecordRequest request
    ) {
        return ResponseEntity.ok("기록 수정 완료");
    }

    @Operation(summary = "기록 삭제", description = "기존 기록을 삭제합니다.")
    @DeleteMapping("/{recordIdx}")
    public ResponseEntity<String> deleteRecord(
            @PathVariable Long recordIdx
    ) {
        return ResponseEntity.ok("기록 삭제 완료");
    }

    @Operation(summary = "일일 기록 생성", description = "하루의 특정 반려 동물의 기록을 작성합니다.")
    @PostMapping("/pet/{petIdx}")
    public ResponseEntity<String> createDailyRecord(@RequestBody DailyRecordDto.RegisterDailyRecordRequest registerDailyRecordRequest,
                                                    @PathVariable Long petIdx) {
        return ResponseEntity.ok("ok");
    }

}
