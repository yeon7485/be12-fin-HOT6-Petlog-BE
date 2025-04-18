package com.hot6.backend.chat.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ChatMessageType {
    TEXT("text"),
    PET("pet"),
    SCHEDULE("schedule");

    private final String value;

    ChatMessageType(String value) {
        this.value = value;
    }

    @JsonValue // JSON 직렬화 시 문자열로 출력
    public String getValue() {
        return value;
    }

    public static ChatMessageType from(String value) {
        for (ChatMessageType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown message type: " + value);
    }
}