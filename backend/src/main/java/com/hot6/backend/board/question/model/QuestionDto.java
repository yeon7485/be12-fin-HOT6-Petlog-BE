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
        private String writer;

        @JsonProperty("qTitle")
        private String qTitle;
        private String content;
        private boolean selected;
        private String image;
        private List<String> tags;

        public Question toEntity() {
            return Question.builder()
                    .writer(writer)
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
        private LocalDate created_at;
        private List<String> tags;

        public static QuestionResponse from(Question question) {
            return QuestionResponse.builder()
                    .idx(question.getIdx())
                    .writer(question.getWriter())
                    .qTitle(question.getQTitle())
                    .content(question.getContent())
                    .selected(question.isSelected())
                    .image(question.getImage())
                    .created_at(question.getCreated_at())
                    .tags(question.getHashtagsList() != null ? question
                            .getHashtagsList().stream()
                            .map(Hashtag_Question::getTag)
                            .toList() : List.of())
                            .build();
        }
    }
}
