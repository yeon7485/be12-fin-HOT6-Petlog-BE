package com.hot6.backend.user.model;

public enum UserType {
    NORMAL("ROLE_USER"), ADMIN("ROLE_ADMIN"), SOCIAL("ROLE_SOCIAL");

    private final String role;

    UserType(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}