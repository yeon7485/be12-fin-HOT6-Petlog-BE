package com.hot6.backend.board.answer.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AnswerDto {
    @Getter
    @Setter
    public static class AnswerRequest {
        private Long questionIdx;
        private String content;
    }

    @Getter
    @Builder
    public static class AnswerResponse {
        private Long idx;
        private String content;
        private boolean selected;
        private LocalDate createdAt;
        private String writer;

        public static AnswerResponse from(Answer answer) {
            return AnswerResponse.builder()
                    .idx(answer.getIdx())
                    .content(answer.getContent())
                    .selected(answer.isSelected())
                    .createdAt(LocalDate.from(answer.getCreatedAt()))
                    .writer(answer.getUser().getNickname())
                    .build();
        }
    }
}

