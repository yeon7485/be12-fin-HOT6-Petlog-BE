package com.hot6.backend.category;


import com.hot6.backend.category.model.Category;
import com.hot6.backend.category.model.CategoryDto;
import com.hot6.backend.category.model.CategoryRepository;
import com.hot6.backend.category.model.CategoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void createCategory(CategoryDto.CategoryCreateRequest request) {
        // 문자열 → enum 변환
        CategoryType categoryType;
        try {
            categoryType = CategoryType.valueOf(request.getType());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 카테고리 타입입니다: " + request.getType());
        }

        Category category = Category.builder()
                .name(request.getName())
                .color(request.getColor())
                .description(request.getDescription())
                .type(categoryType)
                .build();

        categoryRepository.save(category);
    }

    public List<CategoryDto.CategoryResponse> getCategoryList(CategoryType categoryType) {
        return categoryRepository.findByType(categoryType).stream()
                .map(category -> CategoryDto.CategoryResponse.builder()
                        .Idx(category.getIdx())
                        .name(category.getName())
                        .color(category.getColor())
                        .build())
                .toList();
    }

    public void updateCategory(Long categoryIdx, CategoryDto.CategoryUpdateRequest request) {
        Category category = categoryRepository.findById(categoryIdx)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        category.setName(request.getName());
        category.setColor(request.getColor());
        category.setDescription(request.getDescription());

        categoryRepository.save(category);
    }

    public void deleteCategory(Long categoryIdx) {
        Category category = categoryRepository.findById(categoryIdx)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        categoryRepository.delete(category);
    }
}
