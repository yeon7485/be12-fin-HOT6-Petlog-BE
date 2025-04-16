package com.hot6.backend.user;

import com.hot6.backend.user.model.CustomOAuth2User;
import com.hot6.backend.user.model.User;
import com.hot6.backend.user.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    // @Bean으로 등록되어 있지 않기 때문에 직접 객체 생성
    private final DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();

    private final UserService userService;

    // 회원 정보를 가져와서 OAuth2User를 반환
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 회원 정보 가져오기
        OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // kakao_account 안에 email 있음
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

        String email = (String) kakaoAccount.get("email");

        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String nickname = (String) profile.get("nickname");
        String profileImage = (String) profile.get("profile_image_url");
        System.out.println(userService.loadUserByUsername(email));

        // 유저가 없으면 회원가입
        if (userService.loadUserByUsername(email) == null) {
            userService.signup(UserDto.CreateRequest.builder()
                    .email(email)
                    .password("oauth2-temp-password")
                    .nickname(nickname)
                    .profileImageUrl(profileImage)
                    .role("USER")
                    .enabled(true)
                    .build());
        }

        User user = (User) userService.loadUserByUsername(email);
        return new CustomOAuth2User(user);
    }
}
