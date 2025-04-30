package com.hot6.backend.config;

import com.hot6.backend.user.model.User;
import com.hot6.backend.user.model.UserType;
import com.hot6.backend.utils.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Value("${frontend-server}")
    private String frontendServer;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        String jwtToken = JwtUtil.generateToken(User.builder()
                .idx(user.getAttribute("idx"))
                .email(user.getAttribute("email"))
                .nickname(user.getAttribute("nickname"))
                .userType(UserType.valueOf("USER"))
                .build());


        ResponseCookie cookie = ResponseCookie
                .from("ATOKEN", jwtToken)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .maxAge(Duration.ofHours(1L))
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect(frontendServer);
    }
}