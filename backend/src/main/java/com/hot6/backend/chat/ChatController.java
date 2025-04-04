package com.hot6.backend.chat;

import com.hot6.backend.chat.model.ChatDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @PostMapping("/")
    public ResponseEntity<String> createGroupChat(@RequestBody ChatDto.CreateChatRoomRequest request) {
        return ResponseEntity.ok("ok");
    }

    @PutMapping("/{chatRoomIdx}")
    ResponseEntity<String> updateGroupChat(
            @PathVariable Long chatRoomIdx,
            @RequestBody ChatDto.UpdateChatRequest request) {
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/{chatRoomIdx}")
    ResponseEntity<String> deleteGroupChat(@PathVariable Long chatRoomIdx) {
        return ResponseEntity.ok("ok");
    }

    @PutMapping("/{chatRoomIdx}/user/{userIdx}")
    ResponseEntity<String> updateUserChat(@PathVariable Long userIdx) {
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/")
    public ResponseEntity<List<ChatDto.ChatInfo>> getChatList() {
        List<ChatDto.ChatInfo> list = new ArrayList<>();
        list.add(ChatDto.ChatInfo.builder()
                .title("Title01")
                .hashtags(List.of("hash01", "hash02", "hash03"))
                .build());

        list.add(ChatDto.ChatInfo.builder()
                .title("Title02")
                .hashtags(List.of("hash03", "hash04", "hash05"))
                .build());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ChatDto.ChatInfo>> searchChat(@RequestBody ChatDto.ChatSearchRequest request) {
        List<ChatDto.ChatInfo> list = new ArrayList<>();
        list.add(ChatDto.ChatInfo.builder()
                .title("Title01")
                .hashtags(List.of("hash01", "hash02", "hash03"))
                .build());

        list.add(ChatDto.ChatInfo.builder()
                .title("Title02")
                .hashtags(List.of("hash03", "hash04", "hash05"))
                .build());
        return ResponseEntity.ok(list);
    }

    @PostMapping("/chatroom/{charRoomIdx}/chat")
    public ResponseEntity<String> createChat(@PathVariable Long charRoomIdx,
                                             @RequestBody ChatDto.CreateChatRequest request) {
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/chatroom/{chatRoomIdx}/chat")
    public ResponseEntity<List<ChatDto.ChatElement>> createChat(@PathVariable Long chatRoomIdx) {
        List<ChatDto.ChatElement> list = new ArrayList<>();
        list.add(ChatDto.ChatElement.builder()
                .createdAt("2025-03-31")
                .message("test_message_01")
                        .nickname("test01")
                        .userIdx(1L)
                .build());
        list.add(ChatDto.ChatElement.builder()
                .createdAt("2025-03-31")
                .message("test_message_02")
                .nickname("test02")
                .userIdx(1L)
                .build());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/chatroom")
    public ResponseEntity<List<ChatDto.ChatInfo>> getChatRoomList(@PathVariable Long charRoomIdx) {
        List<ChatDto.ChatInfo> list = new ArrayList<>();
        list.add(ChatDto.ChatInfo.builder()
                .title("Title01")
                .hashtags(List.of("hash01", "hash02", "hash03"))
                .build());

        list.add(ChatDto.ChatInfo.builder()
                .title("Title02")
                .hashtags(List.of("hash03", "hash04", "hash05"))
                .build());
        return ResponseEntity.ok(list);
    }
}
