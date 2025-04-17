package com.hot6.backend.user.model;

import com.hot6.backend.pet.model.PetDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class UserDto {

    @Getter
    @Builder
    @Schema(description = "회원가입 요청 DTO")
    public static class CreateRequest {
        @Schema(description = "이메일", example = "user@example.com")
        private String email;

        @Schema(description = "비밀번호 (영어, 숫자, 특수문자 포함 8자 이상)", example = "Password123!")
        private String password;

        @Schema(description = "닉네임", example = "happyDogLover")
        private String nickname;

        @Schema(description = "프로필 이미지 URL (선택사항)", example = "https://example.com/image.png")
        private String profileImageUrl;

        @Schema(description = "회원 역할 (USER or ADMIN)", example = "USER")
        private String role;

        @Schema(description = "이메일 인증 여부", example = "false")
        private boolean enabled;

        public User toEntity(String password) {
            return User.builder()
                    .email(email)
                    .password(password)
                    .nickname(nickname)
                    .userType(UserType.valueOf(role))
                    .userProfileImage(profileImageUrl)
                    .enabled(enabled)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class CreateResponse {
        @Schema(description = "회원가입된 이메일", example = "user@example.com")
        private String email;

        public static CreateResponse from(User user) {
            return CreateResponse.builder().email(user.getEmail()).build();
        }
    }

    @Getter
    public static class SocialRegisterRequest {
        @Schema(description = "소셜 이메일", example = "kakao_user@example.com")
        private String email;

        @Schema(description = "소셜 닉네임", example = "카카오유저")
        private String nickname;

        @Schema(description = "소셜 프로필 이미지", example = "https://kakao.com/user/profile.jpg")
        private String profileImageUrl;

        @Schema(description = "소셜 제공자 (예: kakao)", example = "kakao")
        private String socialProvider;

        @Schema(description = "소셜 액세스 토큰", example = "abcdefg1234567")
        private String accessToken;
    }

    @Getter
    public static class LoginRequest {
        @Schema(description = "로그인 이메일", example = "user@example.com")
        private String email;

        @Schema(description = "로그인 비밀번호", example = "Password123!")
        private String password;
    }

    @Getter
    @Builder
    public static class LoginResponse {
        @Schema(description = "JWT 액세스 토큰", example = "eyJhbGciOiJIUzI1NiIs...")
        private String accessToken;
    }

    @Getter
    @Builder
    public static class UserProfileResponse {
        @Schema(description = "이메일", example = "user@example.com")
        private String email;

        @Schema(description = "닉네임", example = "happyDogLover")
        private String nickname;

        @Schema(description = "프로필 이미지 URL", example = "https://example.com/image.png")
        private String profileImageUrl;

        @Schema(description = "회원의 펫 카드 리스트")
        private List<PetDto.PetCard> petCards;
    }

    @Getter
    public static class UserProfileImageUpdateRequest {
        @Schema(description = "새 프로필 이미지 URL", example = "https://example.com/updated.png")
        private String profileImageUrl;
    }

    @Getter
    public static class PasswordUpdateRequest {
        @Schema(description = "현재 비밀번호", example = "CurrentPassword123!")
        private String currentPassword;

        @Schema(description = "새 비밀번호", example = "NewPassword456!")
        private String newPassword;
    }

    @Getter
    public static class UserDeleteRequest {
        @Schema(description = "회원 이메일", example = "user@example.com")
        private String email;

        @Schema(description = "회원 비밀번호", example = "Password123!")
        private String password;
    }

    @Getter
    @Builder
    public static class LoginCheckResponse {
        @Schema(description = "로그인 여부", example = "true")
        private boolean isLogin;

        @Schema(description = "회원 닉네임", example = "user")
        private String nickname;

        @Schema(description = "이메일 인증 여부", example = "false")
        private boolean enabled;

        @Schema(description = "회원 역할 (USER or ADMIN)", example = "USER")
        private String role;

        public static LoginCheckResponse from(User user) {
            return LoginCheckResponse.builder()
                    .isLogin(true)
                    .nickname(user.getNickname())
                    .enabled(user.isEnabled())
                    .role(String.valueOf(user.getUserType()))
                    .build();
        }
    }

}
