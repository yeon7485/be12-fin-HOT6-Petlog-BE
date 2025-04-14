package com.hot6.backend.chat;

import com.hot6.backend.chat.model.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {
    private final SimpMessagingTemplate simp;

    @MessageMapping("/chat/{roomIdx}")
    public void sendMessage(@DestinationVariable Long roomIdx, @Payload ChatDto.ChatMessageDto dto) {
        simp.convertAndSend("/topic/" + roomIdx, dto);
    }
}
