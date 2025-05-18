package com.hot6.backend.config;

import com.hot6.backend.config.filter.JwtChannelInterceptor;
import com.hot6.backend.config.filter.JwtHandshakeInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final JwtHandshakeInterceptor handshakeInterceptor;
    private final JwtChannelInterceptor jwtChannelInterceptor;
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws") //stomp 엔드포인트
                .addInterceptors(handshakeInterceptor)
                .setAllowedOriginPatterns("*")
                .withSockJS();

        registry
                .addEndpoint("/ws")
                .addInterceptors(handshakeInterceptor)
                .setAllowedOriginPatterns("*");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(jwtChannelInterceptor); // 메시지 인터셉터 등록
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");  // 구독 할 때 사용
        registry.setApplicationDestinationPrefixes("/app");     //메시지 보낼 때 사용
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.addDecoratorFactory(handler -> new WebSocketHandlerDecorator(handler) {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                log.info("✅ WebSocket 연결됨: {}", session.getId());
                super.afterConnectionEstablished(session);
            }

            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
                log.info("❌ WebSocket 연결 종료: {} (이유: {})", session.getId(), closeStatus);
                super.afterConnectionClosed(session, closeStatus);
            }
        });
    }
}
