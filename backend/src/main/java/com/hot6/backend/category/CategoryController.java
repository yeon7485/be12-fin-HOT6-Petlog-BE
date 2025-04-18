package com.hot6.backend.category;


import com.hot6.backend.category.model.CategoryDto;
import com.hot6.backend.category.model.CategoryType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/category")
@Tag(name = "Category", description = "카테고리 관리 API")
public class CategoryController {
    @Operation(summary = "카테고리 목록 조회", description = "카테고리 목록을 조회합니다.")
    @GetMapping("/list")
    public ResponseEntity<List<CategoryDto.CategoryResponse>> getCategoryList(
            @RequestParam CategoryType categoryType
    ) {
        List<CategoryDto.CategoryResponse> result = new ArrayList<>();

        result.add(
                CategoryDto.CategoryResponse.builder()
                        .categoryIdx(1L)
                        .categoryName("병원")
                        .color("#FF8A65")
                        .build()
        );

        result.add(
                CategoryDto.CategoryResponse.builder()
                        .categoryIdx(2L)
                        .categoryName("미용실")
                        .color("#4DB6AC")
                        .build()
        );

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "카테고리 생성", description = "관리자가 새로운 카테고리를 생성합니다.")
    @PostMapping("/category-register")
    public ResponseEntity<String> createRecordCategory(@RequestBody CategoryDto.CategoryCreateRequest request) {
        return ResponseEntity.ok("카테고리 생성 완료");
    }

    @Operation(summary = "카테고리 수정", description = "기존 카테고리를 수정합니다.")
    @PutMapping("/{categoryIdx}")
    public ResponseEntity<String> updateRecordCategory(@PathVariable Long categoryIdx,
                                                       @RequestBody CategoryDto.CategoryCreateRequest request) {
        return ResponseEntity.ok("카테고리 수정 완료");
    }

    @Operation(summary = "카테고리 삭제", description = "카테고리를 삭제합니다. 이미 사용 중인 카테고리는 '기타'로 이동됩니다.")
    @DeleteMapping("/{categoryIdx}")
    public ResponseEntity<String> deleteRecordCategory(@PathVariable Long categoryIdx) {
        return ResponseEntity.ok("카테고리 삭제 완료");
    }
}
