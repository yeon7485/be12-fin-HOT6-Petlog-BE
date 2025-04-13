package com.hot6.backend.chat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class ChatMessageDto {
        private Long senderId;
        private Long chatroomId;
        private String type;        // "text", "image", "file" 등
        private String text;        // 본문 내용 or Base64 or URL
        private String timestamp;   // ISO String (정렬용, 표시용)
    }

}
