package com.hot6.backend.board;

import com.hot6.backend.board.comment.model.CommentDto;
import com.hot6.backend.board.model.BoardDto;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {
    @GetMapping("/category")
    public ResponseEntity<List<BoardDto.CategoryResponse>> getCategoryList() {
        return ResponseEntity.ok(List.of(
                BoardDto.CategoryResponse.builder().categoryIdx(1L).categoryName("정보 공유").build(),
                BoardDto.CategoryResponse.builder().categoryIdx(2L).categoryName("분양 홍보").build()
        ));
    }

    @PostMapping("/category")
    public ResponseEntity<String> registerCategory(@RequestBody BoardDto.CategoryRegisterRequest registerRequest) {
        return ResponseEntity.ok("ok");
    }

    @PutMapping("/category/{categoryIdx}")
    public ResponseEntity<String> updateCategory(
            @PathVariable Long categoryIdx,
            @RequestBody BoardDto.CategoryRegisterRequest registerRequest) {
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/category/{categoryIdx}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryIdx) {
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/category/{categoryIdx}/{boardIdx}")
    public ResponseEntity<BoardDto.BoardDetailResponse> getBoardDetail(
            @PathVariable Long categoryIdx,
            @PathVariable Long boardIdx
    ) {
        return ResponseEntity.ok(BoardDto.BoardDetailResponse.builder()
                .idx(1L)
                .title("test")
                .content("test")
                .authorName("test")
                .createdAt("2025-03-31")
                .comments(
                        List.of(
                                CommentDto.CommentElement.builder()
                                        .comment("comment1")
                                        .build(),
                                CommentDto.CommentElement.builder()
                                        .comment("comment2")
                                        .build()
                        )
                )
                .build());
    }

    @PutMapping("/{boardIdx}")
    public ResponseEntity<BoardDto.BoardDetailResponse> updateBoard(@PathVariable Long boardIdx){
        return ResponseEntity.ok(BoardDto.BoardDetailResponse.builder()
                .idx(1L)
                .title("test")
                .content("test")
                .authorName("test")
                .createdAt("2025-03-31")
                .comments(
                        List.of(
                                CommentDto.CommentElement.builder()
                                        .comment("comment1")
                                        .build(),
                                CommentDto.CommentElement.builder()
                                        .comment("comment2")
                                        .build()
                        )
                )
                .build());
    }

    @DeleteMapping("/{boardIdx}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long boardIdx) {
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/{boardIdx}/comment")
    public ResponseEntity<String> registerComment(@PathVariable Long boardIdx,
                                                  @RequestBody CommentDto.RegisterRequest commentDto) {
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/search")
    public ResponseEntity<List<BoardDto.BoardInfo>> searchResult(@RequestParam String search) {
        List<BoardDto.BoardInfo> list = new ArrayList<>();
        list.add(BoardDto.BoardInfo.builder()
                .authorName("user01")
                .title("test01")
                .createdAt("2025-03-31")
                .idx(1L)
                .build());

        list.add(BoardDto.BoardInfo.builder()
                .authorName("user01")
                .title("test02")
                .createdAt("2025-03-31")
                .idx(2L)
                .build());

        return ResponseEntity.ok(list);
    }

    @GetMapping("/question")
    public ResponseEntity<List<BoardDto.BoardInfo>> getQuestionList() {
        List<BoardDto.BoardInfo> list = new ArrayList<>();
        list.add(BoardDto.BoardInfo.builder()
                .authorName("user01")
                .title("test01")
                .createdAt("2025-03-31")
                .idx(1L)
                .build());

        list.add(BoardDto.BoardInfo.builder()
                .authorName("user01")
                .title("test02")
                .createdAt("2025-03-31")
                .idx(2L)
                .build());

        return ResponseEntity.ok(list);
    }

    @PostMapping("/question")
    public ResponseEntity<String> registerQuestion(@RequestBody BoardDto.RegisterQuestionRequest request){
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/question/{questionIdx}/answer")
    public ResponseEntity<String> registerAnswer(@PathVariable Long questionIdx,
                                                 @RequestBody BoardDto.RegisterAnswerRequest request){
        return ResponseEntity.ok("ok");
    }

    @PutMapping("/question/{questionIdx}/answer")
    public ResponseEntity<String> updateAnswer(@PathVariable Long questionIdx,
                                               @RequestBody BoardDto.RegisterAnswerRequest request){
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/question/{questionIdx}/answer")
    public ResponseEntity<String> deleteAnswer(@PathVariable Long questionIdx){
        return ResponseEntity.ok("ok");
    }
}
