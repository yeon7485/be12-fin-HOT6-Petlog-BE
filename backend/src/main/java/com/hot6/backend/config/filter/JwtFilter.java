package com.hot6.backend.config.filter;


import com.hot6.backend.user.model.User;
import com.hot6.backend.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("JwtFilter 실행됐다.");
        Cookie[] cookies = request.getCookies();

        String jwtToken = null;
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("ATOKEN")) {
                    jwtToken = cookie.getValue();
                }
            }
        }

        if(jwtToken != null) {
            User users = JwtUtil.getUser(jwtToken);

            if(users != null) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(users, null, users.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(users);

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/login") || path.startsWith("/app/users/signup");
    }
}
