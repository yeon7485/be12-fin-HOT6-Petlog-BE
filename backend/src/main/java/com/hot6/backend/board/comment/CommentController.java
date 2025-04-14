package com.hot6.backend.board.comment;

import com.hot6.backend.board.comment.model.CommentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/comment")
@Tag(name = "Comment", description = "댓글 관련 API")
public class CommentController {

    @Operation(summary = "댓글 작성", description = "해당 게시글에 댓글을 작성합니다.")
    @PostMapping("/{postIdx}")
    public ResponseEntity<String> registerComment(
            @Parameter(description = "게시글 ID") @PathVariable Long postIdx,
            @RequestBody CommentDto.CommentRegisterRequest commentDto) {
        return ResponseEntity.ok("댓글 작성 완료");
    }

    @Operation(summary = "댓글 수정", description = "댓글 ID를 기준으로 댓글 내용을 수정합니다.")
    @PutMapping("/{idx}")
    public ResponseEntity<String> updateComment(
            @Parameter(description = "댓글 ID", example = "1") @PathVariable Long idx,
            @RequestBody CommentDto.CommentRegisterRequest updateRequest) {
        return ResponseEntity.ok("댓글 수정 완료");
    }

    @Operation(summary = "댓글 삭제", description = "댓글 ID를 기준으로 댓글을 삭제합니다.")
    @DeleteMapping("/{idx}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long idx) {
        return ResponseEntity.ok("댓글 삭제 완료");
    }

}
