package com.hot6.backend.config.filter;

import com.hot6.backend.user.model.User;
import com.hot6.backend.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        StompCommand command = accessor.getCommand();
        log.info("📡 STOMP Command: {}", command);
        if (StompCommand.CONNECT.equals(command) || StompCommand.SEND.equals(command)) {
            log.info("🔌 WebSocket {} 요청 들어옴",command);
            log.info("Headers: {}", accessor.toNativeHeaderMap());
            User user = (User) accessor.getSessionAttributes().get("user");


            if (user != null) {
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                log.info("User: {},{}", user.getIdx(),command);
                accessor.setUser(auth); // Principal 설정
                // 🔥 추가: SecurityContext에 Authentication 저장

                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(auth);
                SecurityContextHolder.setContext(context);
            }
        }


        if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            String destination = accessor.getDestination();
            log.info("📩 SUBSCRIBE 요청: {}", destination);

            if (destination != null && destination.startsWith("/topic/chat/room/")) {
                String roomId = destination.substring("/topic/chat/room/".length());
                log.info("📌 구독된 roomId: {}", roomId);
            }
        }

        log.info("✅ ChannelInterceptor 세션 유저 확인: {}", accessor.getSessionAttributes());

        return message;
    }
}