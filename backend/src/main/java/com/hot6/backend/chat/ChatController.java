package com.hot6.backend.chat;

import com.hot6.backend.chat.model.ChatDto;
import com.hot6.backend.chat.service.ChatRoomParticipantService;
import com.hot6.backend.chat.service.ChatRoomService;
import com.hot6.backend.common.BaseResponse;
import com.hot6.backend.common.BaseResponseStatus;
import com.hot6.backend.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@Tag(name = "Chat", description = "그룹 채팅 기능 API")
public class ChatController {
    private final ChatRoomService chatRoomService;
    private final ChatRoomParticipantService chatRoomParticipantService;

    @Operation(summary = "그룹 채팅방 생성", description = "채팅방 제목과 해시태그를 포함하여 새로운 채팅방을 생성합니다.")
    @PostMapping
    public ResponseEntity<String> createGroupChat(@RequestBody ChatDto.CreateChatRoomRequest request, @AuthenticationPrincipal User user) {
        log.info("user info {}", user.getIdx());
        chatRoomService.createChatRoom(request, user.getIdx());
        return ResponseEntity.ok("채팅방 생성 완료");
    }

    @Operation(summary = "그룹 채팅방 수정", description = "채팅방 제목과 해시태그를 수정합니다.")
    @PutMapping("/{chatRoomIdx}")
    public ResponseEntity<String> updateGroupChat(
            @PathVariable Long chatRoomIdx,
            @RequestBody ChatDto.UpdateChatRequest request) {
        return ResponseEntity.ok("채팅방 수정 완료");
    }

    @Operation(summary = "그룹 채팅방 삭제", description = "채팅방을 삭제합니다.")
    @DeleteMapping("/{chatRoomIdx}")
    public ResponseEntity<String> deleteGroupChat(
             @PathVariable Long chatRoomIdx) {
        return ResponseEntity.ok("채팅방 삭제 완료");
    }

    @Operation(summary = "채팅방 나가기", description = "사용자가 채팅방에서 나갑니다.")
    @DeleteMapping("/{chatRoomIdx}/user/{userIdx}")
    public ResponseEntity<String> leaveChatRoom(
            @PathVariable Long chatRoomIdx,
            @PathVariable Long userIdx) {
        return ResponseEntity.ok("채팅방 나가기 완료");
    }

    @Operation(summary = "전체 채팅방 목록 조회", description = "전체 그룹 채팅방 리스트를 조회합니다.")
    @GetMapping("/")
    public ResponseEntity<BaseResponse<List<ChatDto.ChatInfo>>> getChatList() {
        return ResponseEntity.ok(new BaseResponse(BaseResponseStatus.SUCCESS, chatRoomService.getList()));
    }

    @Operation(summary = "참여 중인 채팅방 목록 조회", description = "사용자가 현재 참여 중인 채팅방 목록을 조회합니다.")
    @GetMapping("/chatrooms/me")
    public ResponseEntity<BaseResponse<List<ChatDto.ChatInfo>>> getUserChatRooms(
            @AuthenticationPrincipal User user) {
//        return ResponseEntity.ok(new BaseResponse(BaseResponseStatus.SUCCESS, chatRoomService.getChatRoomByUserIdx(user.getIdx())));
        return ResponseEntity.ok(new BaseResponse(BaseResponseStatus.SUCCESS, chatRoomService.findMyChatRooms(user.getIdx())));
    }

    @Operation(summary = "채팅방 검색", description = "채팅방 제목 또는 해시태그로 검색합니다.")
    @GetMapping("/search")
    public ResponseEntity<List<ChatDto.ChatInfo>> searchChat(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) List<String> hashtags) {

        List<ChatDto.ChatInfo> list = List.of(
                ChatDto.ChatInfo.builder().title("Title01").hashtags(List.of("#햄스터", "#서울")).build(),
                ChatDto.ChatInfo.builder().title("Title02").hashtags(List.of("#친구", "#펫")).build()
        );
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "채팅방 일정", description = "채팅방의 정보(채팅방 이름,해시 태그, 대화 상대)를 조회 합니다..")
    @GetMapping("/chatroom/{chatRoomIdx}/users")
    public ResponseEntity<BaseResponse<Slice<ChatDto.ChatUserInfo>>> getChatRoomUsers(
            @PathVariable Long chatRoomIdx,
            @RequestParam(required = false) Long lastUserId,
            @RequestParam(defaultValue = "20") int size
    ) {
        Slice<ChatDto.ChatUserInfo> users = chatRoomParticipantService.getChatRoomInUsers(chatRoomIdx, lastUserId, size);
        return ResponseEntity.ok(new BaseResponse(BaseResponseStatus.SUCCESS,users));
    }

    @Operation(summary = "채팅 메시지 조회", description = "지정된 채팅방의 이전 메시지를 조회합니다.")
    @GetMapping("/chatroom/{chatRoomIdx}/chat")
    public ResponseEntity<List<ChatDto.ChatElement>> getChatMessages(
            @PathVariable Long chatRoomIdx) {
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

    @Operation(
            summary = "WebSocket 채팅 메시지 수신",
            description = """
        이 기능은 WebSocket 기반으로 구현되며, STOMP 프로토콜을 사용합니다.<br><br>
        - 연결 주소: `ws://{server}/ws/chat`<br>
        - 구독 주소: `/topic/chatroom/{chatRoomIdx}`<br>
        - 전송 주소: `/app/chat.sendMessage`<br>
        - 메시지 형식: JSON<br>
        - 실시간 메시지 전송 및 수신은 Swagger에서 테스트할 수 없습니다. <br><br>
        """
    )
    @GetMapping("/chatroom/ws-doc")
    public ResponseEntity<String> websocketDoc() {
        return ResponseEntity.ok("Swagger 문서용 WebSocket 설명");
    }
}
