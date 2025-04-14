package com.hot6.backend.chat;

import com.hot6.backend.chat.model.ChatDto;
import com.hot6.backend.user.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChatWebSocketControllerTest {
    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private ChatWebSocketController controller;

    UserDetails userDetails;

    @BeforeEach
    void setUp() {
        userDetails = new CustomUserDetails(
                1L,
                "tester",
                "pw",
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    @Test
    void 채팅_메시지를_정상적으로_브로커로_전달한다() {
        // given
        Long roomIdx = 123L;
        ChatDto.ChatMessageDto dto = ChatDto.ChatMessageDto.builder()
                .senderId(1L)
                .text("test")
                .chatroomId(roomIdx)
                .timestamp("2020-01-01T00:00:00.000")
                .build();
        // when
        controller.sendMessage(roomIdx, dto, User.builder()
                .idx(1L)
                .email("test@test.com")
                .password("qwer1234")
                .build());

        // then
        verify(messagingTemplate).convertAndSend("/topic/" + roomIdx, dto);
    }
}