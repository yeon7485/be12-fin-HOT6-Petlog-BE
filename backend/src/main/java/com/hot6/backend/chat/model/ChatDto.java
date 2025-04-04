package com.hot6.backend.chat.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class ChatDto {

    @Getter
    public static class CreateChatRoomRequest {
        private String title;
        private List<String> hashtags;
        private List<Long> users;
    }

    @Getter
    public static class UpdateChatRequest {
        private String title;
        private List<String> hashtags;
    }

    @Getter
    @Builder
    public static class ChatInfo{
        public String title;
        public List<String> hashtags;
    }

    @Getter
    @Builder
    public static class ChatElement {
        public String message;
        public String nickname;
        public Long userIdx;
        public String createdAt;
    }

    @Getter
    public static class ChatSearchRequest {
        public String query;
        public List<String> hashtags;
    }

    @Getter
    public static class CreateChatRequest {
        public String message;
        public String nickname;
        public Long userIdx;
        public String createdAt;
    }
}
