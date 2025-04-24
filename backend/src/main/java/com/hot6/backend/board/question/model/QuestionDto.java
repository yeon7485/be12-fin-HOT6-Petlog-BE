package com.hot6.backend.board.question.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hot6.backend.board.hashtagQuestion.model.Hashtag_Question;
import com.hot6.backend.board.question.images.QuestionImage;
import com.hot6.backend.pet.model.PetSummary;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class QuestionDto {

    @Getter
    public static class QuestionRequest {
        @JsonProperty("qTitle")
        private String qTitle;
        private String content;
        private boolean selected;
        private List<String> tags;
        private List<Long> petIdxList;
        private List<String> removedImageUrls;

        public Question toEntity() {
            return Question.builder()
                    .qTitle(qTitle)
                    .content(content)
                    .selected(selected)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class QuestionResponse {
        private Long idx;
        private String writer;
        @JsonProperty("qTitle")
        private String qTitle;
        private String content;
        private boolean selected;
        private LocalDate createdAt;
        private List<String> tags;
        private int answerCount;
        private List<String> imageUrls;
        private String profileImageUrl;
        private List<PetSummary> petList;

        public static QuestionResponse from(Question question, int answerCount) {
            return QuestionResponse.builder()
                    .idx(question.getIdx())
                    .writer(question.getUser() != null ? question.getUser().getNickname() : null)
                    .qTitle(question.getQTitle())
                    .content(question.getContent())
                    .selected(question.isSelected())
                    .createdAt(LocalDate.from(question.getCreatedAt()))
                    .tags(question.getHashtagsList() != null
                            ? question.getHashtagsList().stream().map(Hashtag_Question::getTag).toList()
                            : List.of())
                    .answerCount(answerCount)
                    .imageUrls(question.getQuestionImageList() != null
                            ? question.getQuestionImageList().stream()
                            .map(QuestionImage::getUrl)
                            .toList()
                            : List.of())
                    .profileImageUrl(question.getUser() != null ? question.getUser().getUserProfileImage() : null)
                    .petList(question.getPetList() != null
                            ? question.getPetList().stream().map(PetSummary::from).toList()
                            : List.of())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class UserQuestionResponse {
        private Long idx;
        private String writer;
        private String qTitle;
        private String content;
        private boolean selected;
        private LocalDate createdAt;
        private String profileImageUrl;
        private List<String> tags;
        private int answerCount;


        public static UserQuestionResponse from(Question question, int answerCount) {
            return UserQuestionResponse.builder()
                    .idx(question.getIdx())
                    .writer(question.getUser().getNickname())
                    .qTitle(question.getQTitle())
                    .content(question.getContent())
                    .selected(question.isSelected())
                    .createdAt(LocalDate.from(question.getCreatedAt()))
                    .profileImageUrl(question.getUser() != null ? question.getUser().getUserProfileImage() : null)
                    .tags(question.getHashtagsList() != null
                            ? question.getHashtagsList().stream().map(Hashtag_Question::getTag).toList()
                            : List.of())
                    .answerCount(answerCount)
                    .build();
        }
    }
}
