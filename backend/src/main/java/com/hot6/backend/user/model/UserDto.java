package com.hot6.backend.user.model;

import com.hot6.backend.pet.model.PetDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class UserDto {

    @Getter
    public static class RegisterRequest{
        private String email;
        private String password;
        private String nickname;
        private String profileImageUrl;
        private String role; // 일반회원/관리자
    }

    @Getter
    @Builder
    public static class RegisterResponse{
        private String email;
    }

    @Getter
    public static class SocialRegisterRequest{
        private String email;
        private String nickname;
        private String profileImageUrl;
        private String socialProvider; // ex: kakao
        private String accessToken;
    }

    @Getter
    public static class LoginRequest{
        private String email;
        private String password;
    }

    @Getter
    @Builder
    public static class LoginResponse{
        private String accessToken;
    }

    @Getter
    @Builder
    public static class UserProfileResponse{
        private String email;
        private String nickname;
        private String profileImageUrl;
        private List<PetDto.PetCard> petCards;
    }

    @Getter
    public static class UserProfileImageUpdateRequest{
        private String profileImageUrl;
    }

    @Getter
    public static class PasswordUpdateRequest{
        private String currentPassword;
        private String newPassword;
    }

    @Getter
    public static class UserDeleteRequest{
        private String email;
        private String password;
    }

}
