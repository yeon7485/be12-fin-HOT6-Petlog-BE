package com.hot6.backend.category;


import com.hot6.backend.category.model.CategoryDto;
import com.hot6.backend.category.model.CategoryType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
@Tag(name = "Category", description = "카테고리 관리 API")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "카테고리 목록 조회", description = "카테고리 목록을 조회합니다.")
    @GetMapping("/list")
    public ResponseEntity<List<CategoryDto.CategoryResponse>> getCategoryList(
            @RequestParam CategoryType categoryType
    ) {
        return ResponseEntity.ok(categoryService.getCategoryList(categoryType));
    }

    @Operation(summary = "카테고리 생성", description = "관리자가 새로운 카테고리를 생성합니다.")
    @PostMapping("/register")
    public ResponseEntity<String> createRecordCategory(
            @RequestBody CategoryDto.CategoryCreateRequest request) {
        categoryService.createCategory(request);
        return ResponseEntity.ok("카테고리 생성 완료");
    }

    @Operation(summary = "카테고리 수정", description = "기존 카테고리를 수정합니다.")
    @PutMapping("/{categoryIdx}")
    public ResponseEntity<String> updateRecordCategory(
            @PathVariable Long categoryIdx,
            @RequestBody CategoryDto.CategoryUpdateRequest request) {

        categoryService.updateCategory(categoryIdx, request); // ✅ 수정 로직 호출
        return ResponseEntity.ok("카테고리 수정 완료");
    }

    @DeleteMapping("/{categoryIdx}")
    public ResponseEntity<String> deleteRecordCategory(@PathVariable Long categoryIdx) {
        categoryService.deleteCategory(categoryIdx); // ← 이 줄 추가해야 실제 삭제됨
        return ResponseEntity.ok("카테고리 삭제 완료");
    }
}
