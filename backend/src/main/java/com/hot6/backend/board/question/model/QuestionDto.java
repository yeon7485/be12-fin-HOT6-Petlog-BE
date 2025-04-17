package com.hot6.backend.board.question.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hot6.backend.board.hashtagQuestion.model.Hashtag_Question;
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
        private String image;
        private List<String> tags;

        public Question toEntity() {
            return Question.builder()
                    .qTitle(qTitle)
                    .content(content)
                    .selected(selected)
                    .image(image).build();
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
        private String image;
        private LocalDate createdAt;
        private List<String> tags;
        private int answerCount;

        public static QuestionResponse from(Question question, int answerCount) {
            return QuestionResponse.builder()
                    .idx(question.getIdx())
                    .writer(question.getUser() != null ? question.getUser().getNickname() : null)
                    .qTitle(question.getQTitle())
                    .content(question.getContent())
                    .selected(question.isSelected())
                    .image(question.getImage())
                    .createdAt(LocalDate.from(question.getCreatedAt()))
                    .tags(question.getHashtagsList() != null
                            ? question.getHashtagsList().stream()
                            .map(Hashtag_Question::getTag)
                            .toList()
                            : List.of())
                    .answerCount(answerCount)
                    .build();
        }
    }
}
