package com.hot6.backend.place;

import com.hot6.backend.notification.model.NotificationDto;
import com.hot6.backend.place.model.PlaceDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/place")
@Tag(name = "Place", description = "위치 기반 장소 탐색 API")
public class PlaceController {

    @Operation(summary = "내 위치 조회", description = "브라우저 권한을 통해 현재 위치를 조회합니다.")
    @GetMapping("/me")
    public ResponseEntity<PlaceDto.MyLocation> getMyLocation() {
        return ResponseEntity.ok(
                PlaceDto.MyLocation.builder()
                        .lat(37.5665)
                        .lng(126.9780)
                        .build()
        );
    }

    @Operation(summary = "내 주변 장소 조회", description = "현재 위치를 기준으로 주변 반려동물 관련 장소를 조회합니다.")
    @GetMapping("/nearby")
    public ResponseEntity<List<PlaceDto.PlaceInfo>> getNearbyPlaces(
            @RequestParam double lat,
            @RequestParam double lng) {
        List<PlaceDto.PlaceInfo> list = List.of(
                PlaceDto.PlaceInfo.builder()
                        .name("동물병원 A")
                        .address("서울시 마포구")
                        .distance(0.2)
                        .build(),
                PlaceDto.PlaceInfo.builder()
                        .name("펫샵 B")
                        .address("서울시 마포구")
                        .distance(0.5)
                        .build()
        );
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "장소 검색", description = "키워드로 반려동물 관련 장소를 검색합니다.")
    @GetMapping("/search")
    public ResponseEntity<List<PlaceDto.PlaceInfo>> searchPlaces(@RequestParam String keyword) {
        List<PlaceDto.PlaceInfo> list = List.of(
                PlaceDto.PlaceInfo.builder()
                        .name("햄스터 전문점")
                        .address("서울시 용산구")
                        .distance(1.2)
                        .build()
        );
        return ResponseEntity.ok(list);
    }
}
