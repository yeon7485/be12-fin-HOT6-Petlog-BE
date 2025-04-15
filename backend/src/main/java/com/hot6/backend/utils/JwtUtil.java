package com.hot6.backend.utils;

import com.hot6.backend.user.model.User;
import com.hot6.backend.user.model.UserType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtUtil {
    private static final String SECRET = System.getenv("JWT_SECRET");
    private static final int EXP = Integer.parseInt(System.getenv("JWT_EXPIRED"));


    public static User getUser(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return User.builder()
                    .nickname(claims.get("nickname", String.class))
                    .idx(claims.get("idx", Long.class))
                    .userType(UserType.valueOf(claims.get("userType", String.class)))
                    .build();

        } catch (ExpiredJwtException e) {
            System.out.println("토큰이 만료되었습니다!");
        } catch (Exception e) {
            System.out.println("토큰이 유효하지 않습니다!");
        }
        return null;
    }

    public static String generateToken(User user) {
        Claims claims = Jwts.claims();
        claims.put("nickname", user.getNickname());
        claims.put("idx", user.getIdx());
        claims.put("userType", user.getUserType());
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXP))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
        return token;
    }

    public static boolean validate(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.out.println("토큰이 만료되었습니다!");
            return false;
        }
        return true;
    }
}
