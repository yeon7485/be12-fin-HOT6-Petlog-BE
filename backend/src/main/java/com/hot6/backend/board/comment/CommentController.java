package com.hot6.backend.board.comment;

import com.hot6.backend.board.comment.model.CommentDto;
import com.hot6.backend.common.BaseResponse;
import com.hot6.backend.common.BaseResponseStatus;
import com.hot6.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<Void>> create(@RequestBody CommentDto.CommentRequest dto,
                                                     @AuthenticationPrincipal User currentUser) {
        commentService.create(dto, currentUser);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS));
    }

    @GetMapping("/list/{postIdx}")
    public ResponseEntity<BaseResponse<List<CommentDto.CommentResponse>>> list(@PathVariable Long postIdx) {
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS, commentService.list(postIdx)));
    }

    @DeleteMapping("/delete/{idx}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long idx) {
        commentService.delete(idx);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS));
    }

    @PutMapping("/update/{idx}")
    public ResponseEntity<BaseResponse<Void>> update(@PathVariable Long idx, @RequestBody CommentDto.CommentRequest dto) {
        commentService.update(idx, dto);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS));
    }

    @GetMapping("/list/user/{userId}")
    public ResponseEntity<BaseResponse<List<CommentDto.CommentResponse>>> listByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS, commentService.readByAnswer(userId)));
    }
}
