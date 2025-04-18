package com.hot6.backend.board.comment;

import com.hot6.backend.board.answer.model.AnswerDto;
import com.hot6.backend.board.comment.model.CommentDto;
import com.hot6.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@CrossOrigin(origins = "http://localhost:5173")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody CommentDto.CommentRequest dto,
                                       @AuthenticationPrincipal User currentUser) {
        commentService.create(dto,currentUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list/{postIdx}")
    public ResponseEntity<List<CommentDto.CommentResponse>> list(@PathVariable Long postIdx) {
        return ResponseEntity.ok(commentService.list(postIdx));
    }

    @DeleteMapping("/delete/{idx}")
    public ResponseEntity<Void> delete(@PathVariable Long idx) {
        commentService.delete(idx);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{idx}")
    public ResponseEntity<Void> update(@PathVariable Long idx, @RequestBody CommentDto.CommentRequest dto) {
        commentService.update(idx, dto);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/list/user/{userId}")
    public ResponseEntity<List<CommentDto.CommentResponse>> listByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(commentService.readByAnswer(userId));
    }
}

