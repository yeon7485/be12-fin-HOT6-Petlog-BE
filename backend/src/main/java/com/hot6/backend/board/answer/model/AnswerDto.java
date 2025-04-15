package com.hot6.backend.board.answer.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
        private String created_at;

        public static AnswerResponse from(Answer answer) {
            return AnswerResponse.builder()
                    .idx(answer.getIdx())
                    .content(answer.getContent())
                    .selected(answer.isSelected())
                    .created_at(answer.getCreated_at().toString())
                    .build();
        }
    }
}

