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
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            log.info("🔌 WebSocket CONNECT 요청 들어옴");
            log.info("Headers: {}", accessor.toNativeHeaderMap());
            User user = (User) accessor.getSessionAttributes().get("user");

            if (user != null) {
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                accessor.setUser(auth); // Principal 설정
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

        return message;
    }
}