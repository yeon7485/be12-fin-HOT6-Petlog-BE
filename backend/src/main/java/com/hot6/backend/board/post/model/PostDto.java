package com.hot6.backend.board.post.model;

import com.hot6.backend.board.post.images.PostImage;
import com.hot6.backend.category.model.CategoryDto;
import com.hot6.backend.pet.model.Pet;
import com.hot6.backend.pet.model.PetSummary;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PostDto {

    @Getter
    @Setter
    public static class PostRequest {
        private String writer;

        @NotBlank(message = "제목은 필수 입력 항목입니다.")
        private String title;

        @NotBlank(message = "내용은 필수 입력 항목입니다.")
        private String content;
        private Long categoryIdx;
        private String boardType;
        private List<Long> petIdxList;
        private List<String> removedImageUrls;
    }

    @Getter
    @Builder
    public static class PostResponse {
        private Long idx;
        private String writer;
        private String title;
        private String content;
        private String category;
        private LocalDate createdAt;
        private String boardType;
        private List<String> imageUrls;
        private String profileImageUrl;
        private List<PetSummary> petList;

        public static PostResponse from(Post post) {
            return PostResponse.builder()
                    .idx(post.getIdx())
                    .writer(post.getUser().getNickname())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .category(post.getCategory().getName())
                    .createdAt(LocalDate.from(post.getCreatedAt()))
                    .boardType(post.getBoardType().getBoardName())
                    .imageUrls(post.getPostImageList() != null
                            ? post.getPostImageList().stream().map(PostImage::getUrl).toList()
                            : List.of())
                    .profileImageUrl(post.getUser().getUserProfileImage())
                    .petList(post.getPetList() != null
                            ? post.getPetList().stream().map(PetSummary::from).toList()
                            : List.of())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class UserPostResponse {
        private Long idx;
        private String writer;
        private String title;
        private String boardType;
        private LocalDateTime createdAt;

        public static UserPostResponse from(Post post) {
            return UserPostResponse.builder()
                    .idx(post.getIdx())
                    .writer(post.getUser().getNickname())
                    .title(post.getTitle())
                    .boardType(post.getBoardType().getBoardName())
                    .createdAt(post.getCreatedAt())
                    .build();
        }
    }
}
