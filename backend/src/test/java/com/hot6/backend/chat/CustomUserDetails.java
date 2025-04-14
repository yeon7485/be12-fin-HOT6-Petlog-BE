package com.hot6.backend.chat;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private Long userId;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // username은 로그인 ID로 간주
    @Override
    public String getUsername() {
        return username;
    }

    // password는 시큐리티 내부 검증용
    @Override
    public String getPassword() {
        return password;
    }

    // 아래는 모두 true 고정 (계정 만료, 잠금, 비밀번호 만료, 활성화 여부)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
