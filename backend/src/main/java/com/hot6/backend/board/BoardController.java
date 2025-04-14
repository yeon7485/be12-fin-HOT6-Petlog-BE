package com.hot6.backend.board;

import com.hot6.backend.board.comment.model.CommentDto;
import com.hot6.backend.board.model.BoardCategory;
import com.hot6.backend.board.model.BoardDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/board")
@Tag(name = "Board", description = "게시판 관련 API")
public class BoardController {
    @Operation(summary = "게시판  목록 조회", description = "모든 게시판 종류 목록을 조회합니다.")
    @GetMapping("/board-type")
    public ResponseEntity<List<BoardDto.CategoryResponse>> getCategoryList() {
        return ResponseEntity.ok(List.of(
                BoardDto.CategoryResponse.builder().categoryIdx(1L).categoryName("정보 공유").build(),
                BoardDto.CategoryResponse.builder().categoryIdx(2L).categoryName("분양 홍보").build()
        ));
    }

    @Operation(summary = "게시판 종류 등록", description = "새로운 게시판 종류를 생성합니다.")
    @PostMapping("/board-type")
    public ResponseEntity<String> registerCategory(@RequestBody BoardDto.CategoryRegisterRequest registerRequest) {
        return ResponseEntity.ok("ok");
    }

    @Operation(summary = "게시판 종류 이름 수정", description = "기존 게시판 종류의 이름을 수정합니다.")
    @PutMapping("/board-type/{boardTypeIdx}")
    public ResponseEntity<String> updateCategory(
            @PathVariable Long boardTypeIdx,
            @RequestBody BoardDto.CategoryRegisterRequest registerRequest) {
        return ResponseEntity.ok("ok");
    }

    @Operation(summary = "게시판 종류 삭제", description = "특정 게시판 카테고리를 삭제합니다.")
    @DeleteMapping("/board-type/{boardTypeIdx}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long boardTypeIdx) {
        return ResponseEntity.ok("ok");
    }

    @Operation(summary = "각 카테고리별 게시글 전체 조회", description = "카테고리 ID 로 해당 카테고리의 전체 게시글을 조회합니다.")
    @GetMapping("/board-type/{boardTypeIdx}/list")
    public ResponseEntity<List<BoardDto.BoardInfo>> getBoardInCategory(
            @PathVariable Long boardTypeIdx
    ) {
        List<BoardDto.BoardInfo> result = new ArrayList<>();

        result.add(BoardDto.BoardInfo.builder()
                .idx(1L)
                .title("Q&A 질문")
                .authorName("user01")
                .createdAt("2025-04-07")
                        .boardCategory(BoardCategory.DOG)
                .build()
        );

        result.add(BoardDto.BoardInfo.builder()
                .idx(2L)
                .title("정보 공유합니다")
                .authorName("user02")
                .createdAt("2025-04-06")
                        .boardCategory(BoardCategory.CAT)
                .build()
        );

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "게시글 상세 조회", description = "게시글 ID로 게시글 상세 정보를 조회합니다.")
    @GetMapping("/{boardIdx}")
    public ResponseEntity<BoardDto.BoardDetailResponse> getBoardDetail(
            @Parameter(description = "카테고리 ID") @PathVariable Long categoryIdx,
            @Parameter(description = "게시글 ID") @PathVariable Long boardIdx) {
        return ResponseEntity.ok(BoardDto.BoardDetailResponse.builder()
                .idx(boardIdx)
                .title("test")
                .content("test")
                .authorName("test")
                .createdAt("2025-03-31")
                .comments(List.of(
                        CommentDto.CommentElement.builder().comment("comment1").build(),
                        CommentDto.CommentElement.builder().comment("comment2").build()
                )).build());
    }

    @Operation(summary = "게시글 생성", description = "게시글 제목, 내용, 카테고리를 작성하여 새 게시글을 생성합니다.")
    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody BoardDto.BoardCreateRequest request) {
        return ResponseEntity.ok("게시글 등록 완료");
    }

    @Operation(summary = "게시글 수정", description = "기존 게시글의 내용을 수정합니다.")
    @PutMapping("/{postIdx}")
    public ResponseEntity<BoardDto.BoardDetailResponse> updatePost(
            @Parameter(description = "게시글 ID") @PathVariable Long postIdx) {
        return ResponseEntity.ok(BoardDto.BoardDetailResponse.builder()
                .idx(postIdx)
                .title("수정된 제목")
                .content("수정된 내용")
                .authorName("user01")
                .createdAt("2025-03-31")
                .comments(List.of())
                .build());
    }

    @Operation(summary = "게시글 삭제", description = "해당 게시글을 삭제합니다.")
    @DeleteMapping("/{postIdx}")
    public ResponseEntity<String> deleteBoard(@Parameter(description = "게시글 ID") @PathVariable Long postIdx) {
        return ResponseEntity.ok("게시글 삭제 완료");
    }


    @Operation(summary = "게시글 검색", description = "키워드를 이용해 게시글을 검색합니다.")
    @GetMapping("/search")
    public ResponseEntity<List<BoardDto.BoardInfo>> searchResult(
            @Parameter(description = "검색 키워드") @RequestParam String search) {
        List<BoardDto.BoardInfo> list = new ArrayList<>();
        list.add(BoardDto.BoardInfo.builder().authorName("user01").title("test01").createdAt("2025-03-31").idx(1L).build());
        list.add(BoardDto.BoardInfo.builder().authorName("user01").title("test02").createdAt("2025-03-31").idx(2L).build());
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Q&A 질문 목록 조회", description = "질문 게시판의 질문 목록을 조회합니다.")
    @GetMapping("/question")
    public ResponseEntity<List<BoardDto.BoardInfo>> getQuestionList() {
        return ResponseEntity.ok(List.of(
                BoardDto.BoardInfo.builder().authorName("user01").title("test01").createdAt("2025-03-31").idx(1L).build(),
                BoardDto.BoardInfo.builder().authorName("user01").title("test02").createdAt("2025-03-31").idx(2L).build()
        ));
    }

    @Operation(summary = "Q&A 질문 등록", description = "질문 게시글을 등록합니다.")
    @PostMapping("/question")
    public ResponseEntity<String> registerQuestion(@RequestBody BoardDto.RegisterQuestionRequest request) {
        return ResponseEntity.ok("질문 등록 완료");
    }

    @Operation(summary = "Q&A 답변 등록", description = "질문 게시글에 대한 답변을 등록합니다.")
    @PostMapping("/question/{questionIdx}/answer")
    public ResponseEntity<String> registerAnswer(
             @PathVariable Long questionIdx,
            @RequestBody BoardDto.RegisterAnswerRequest request) {
        return ResponseEntity.ok("답변 등록 완료");
    }

    @Operation(summary = "Q&A 답변 수정", description = "기존 답변 내용을 수정합니다.")
    @PutMapping("/question/{questionIdx}/answer/{answerIdx}")
    public ResponseEntity<String> updateAnswer(
             @PathVariable Long questionIdx,
            @PathVariable Long answerIdx,
            @RequestBody BoardDto.RegisterAnswerRequest request) {
        return ResponseEntity.ok("답변 수정 완료");
    }

    @Operation(summary = "Q&A 답변 삭제", description = "질문 게시글에 등록된 답변을 삭제합니다.")
    @DeleteMapping("/question/{questionIdx}/answer/{answerIdx}")
    public ResponseEntity<String> deleteAnswer(
            @PathVariable Long questionIdx,
            @PathVariable Long answerIdx) {
        return ResponseEntity.ok("답변 삭제 완료");
    }
}
