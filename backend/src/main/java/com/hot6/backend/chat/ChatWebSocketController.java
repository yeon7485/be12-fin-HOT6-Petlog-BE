package com.hot6.backend.chat;

import com.hot6.backend.chat.model.ChatDto;
import com.hot6.backend.user.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {
    private final SimpMessagingTemplate simp;

    @MessageMapping("/chat/{roomIdx}")
    public void sendMessage(@DestinationVariable Long roomIdx,
                            @Payload ChatDto.ChatMessageDto dto,
                            Principal principal
                            ) {
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        log.info("💬 [WebSocket 메시지 수신]");
        log.info("📌 roomIdx: {}", roomIdx);
        log.info("👤 sender: {} (user idx: {})", user.getNickname(), user.getIdx());
        log.info("✉️ payload: {}", dto);
        simp.convertAndSend("/topic/chat/" + roomIdx, dto);
    }
}
