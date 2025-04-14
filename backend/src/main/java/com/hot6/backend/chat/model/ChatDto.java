package com.hot6.backend.chat.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ChatDto {
    @Getter
    @Schema(description = "채팅방 생성 요청 DTO")
    public static class CreateChatRoomRequest {
        @Schema(description = "채팅방 제목", example = "햄스터 친구 구해요")
        private String title;

        @Schema(description = "채팅방 해시태그 목록", example = "[\"#햄스터\", \"#김포\", \"#친구\"]")
        private List<String> hashtags;

        @Schema(description = "참여자 사용자 ID 목록", example = "[1, 2, 3]")
        private List<Long> userIds;
    }

    @Getter
    @Schema(description = "채팅방 수정 요청 DTO")
    public static class UpdateChatRequest {
        @Schema(description = "변경할 채팅방 제목", example = "햄스터 사육 정보 공유방")
        private String title;

        @Schema(description = "수정할 해시태그 목록", example = "[\"#햄스터\", \"#정보\"]")
        private List<String> hashtags;
    }

    @Getter
    @Builder
    @Schema(description = "채팅방 정보 응답 DTO")
    public static class ChatInfo {
        @Schema(description = "채팅방 제목", example = "햄스터 친구 구해요")
        public String title;

        @Schema(description = "채팅방 해시태그", example = "[\"#햄스터\", \"#친구\"]")
        public List<String> hashtags;
    }

    @Getter
    @Builder
    @Schema(description = "채팅 메시지 응답 DTO")
    public static class ChatElement {
        @Schema(description = "채팅 메시지 내용", example = "안녕하세요~")
        public String message;

        @Schema(description = "보낸 사용자 닉네임", example = "hamster_lover")
        public String nickname;

        @Schema(description = "보낸 사용자 ID", example = "1")
        public Long userIdx;

        @Schema(description = "보낸 시간", example = "2025-04-07T12:34:56")
        public String createdAt;
    }

    @Getter
    @Schema(description = "채팅방 검색 요청 DTO (POST 방식용)")
    public static class ChatSearchRequest {
        @Schema(description = "검색어", example = "햄스터")
        public String query;

        @Schema(description = "검색할 해시태그 목록", example = "[\"#햄스터\"]")
        public List<String> hashtags;
    }

    @Getter
    @Schema(description = "채팅 메시지 전송 요청 DTO")
    public static class CreateChatRequest {
        @Schema(description = "채팅 메시지", example = "반가워요!")
        public String message;

        @Schema(description = "보낸 사용자 닉네임", example = "user01")
        public String nickname;

        @Schema(description = "채팅방 idx", example = "1")
        public Long chatRoomIdx;

        @Schema(description = "보낸 시간", example = "2025-04-07T12:00:00")
        public String createdAt;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ChatMessageDto {
        private Long senderId;
        private Long chatroomId;
        private String type;        // "text", "image", "file" 등
        private String text;        // 본문 내용 or Base64 or URL
        private String timestamp;   // ISO String (정렬용, 표시용)
    }

}
