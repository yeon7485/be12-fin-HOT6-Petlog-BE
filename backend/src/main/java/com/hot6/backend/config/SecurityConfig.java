package com.hot6.backend.config;


import com.hot6.backend.config.filter.JwtFilter;
import com.hot6.backend.config.filter.LoginFilter;
import com.hot6.backend.user.CustomOAuth2UserService;
import com.hot6.backend.user.OAuth2SuccessHandler;
import com.hot6.backend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationConfiguration configuration;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomOAuth2UserService customOAuth2UserService(UserService userService) {
        return new CustomOAuth2UserService(userService);
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http, CustomOAuth2UserService customOAuth2UserService) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(auth -> auth
                // 인증 없이 접근 가능한 엔드포인트들
                .requestMatchers(
                        "/chat/",
                        "/chat/search",
                        "/chat/chatroom/{chatRoomIdx:[0-9]+}",
                        "/chat/chatroom/ws-doc"
                ).permitAll()

                // 인증이 필요한 채팅 관련 API
                .requestMatchers(
                        "/chat/chatrooms/me",
                        "/chat/chatroom/{chatRoomIdx:[0-9]+}/chat",
                        "/chat/chatroom/{chatRoomIdx:[0-9]+}/users",
                        "/chat/chatroom/{chatRoomIdx:[0-9]+}/leave",
                        "/chat/**"
                ).authenticated()

                // 기타 모든 요청 허용
                .anyRequest().permitAll()
        );
        http.oauth2Login(config -> {
            config.successHandler(new OAuth2SuccessHandler());
            config.userInfoEndpoint(endpoint ->
                    endpoint.userService(customOAuth2UserService)
            );
        });

        // 기존에 사용자한테 설정하도록 한 쿠키(JSESSIONID)를 사용하지 않도록 하는 설정
        http.sessionManagement(AbstractHttpConfigurer::disable);

        http.addFilterAt(new LoginFilter(configuration.getAuthenticationManager()), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
