package com.hot6.backend.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hot6.backend.common.BaseResponse;
import com.hot6.backend.common.BaseResponseStatus;
import com.hot6.backend.common.exception.BaseException;
import com.hot6.backend.user.model.User;
import com.hot6.backend.user.model.UserDto;
import com.hot6.backend.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("LoginFilter 실행됐다.");
        UsernamePasswordAuthenticationToken authToken;

        try {
            UserDto.LoginRequest userDto  = new ObjectMapper().readValue(request.getInputStream(), UserDto.LoginRequest.class);

            authToken =
                    new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword(), null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return authenticationManager.authenticate(authToken);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();

        // ✅ 이메일 인증 안 된 유저는 로그인 실패 처리
        if (!user.getEnabled()) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PrintWriter out = response.getWriter();
            out.print(new ObjectMapper().writeValueAsString(
                    new BaseResponse<>(BaseResponseStatus.EMAIL_VERIFY_FAIL)
            ));
            out.flush();
            return;
        }

        String jwtToken = JwtUtil.generateToken(user);

        ResponseCookie cookie = ResponseCookie
                .from("ATOKEN", jwtToken)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .maxAge(Duration.ofHours(1L))
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.print("{");
        out.print("\"userId\": \"" + user.getNickname() + "\",");
        out.print("\"email\": \"" + user.getEmail() + "\",");
        out.print("\"userType\": \"" + user.getUserType() + "\"");
        out.print("\"enabled\": \"" + user.isEnabled() + "\"");
        out.print("}");
        out.flush();
    }
}
