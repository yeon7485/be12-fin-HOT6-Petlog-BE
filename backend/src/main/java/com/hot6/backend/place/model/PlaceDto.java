package com.hot6.backend.place.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

public class PlaceDto {

    @Getter
    @Builder
    @Schema(description = "현재 위치 좌표")
    public static class MyLocation {
        @Schema(description = "위도", example = "37.5665")
        private double lat;

        @Schema(description = "경도", example = "126.9780")
        private double lng;
    }

    @Getter
    @Builder
    @Schema(description = "장소 정보")
    public static class PlaceInfo {
        @Schema(description = "장소 이름", example = "동물병원 A")
        private String name;

        @Schema(description = "장소 주소", example = "서울시 마포구")
        private String address;

        @Schema(description = "거리 (km)", example = "0.5")
        private double distance;
    }
}
