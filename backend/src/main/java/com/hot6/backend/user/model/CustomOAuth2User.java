package com.hot6.backend.user.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {
    private User user;

    public CustomOAuth2User(User user) {
        this.user = user;
    }

    // 사용자 정보 반환
    @Override
    public Map<String, Object> getAttributes() {
        return Map.of(
                "email", user.getEmail(),
                "nickname", user.getNickname(),
                "userType", user.getUserType()
        );
    }

    // 사용자 이메일 반환
    @Override
    public String getName() {
        return user.getEmail();
    }

//    // 권한이 기본 사용자로 반환되게 설정
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
                Collection<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getUserType().name());

        authorities.add(authority);
        return authorities;
    }
}
