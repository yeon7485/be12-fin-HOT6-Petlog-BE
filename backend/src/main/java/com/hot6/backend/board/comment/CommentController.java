package com.hot6.backend.board.comment;

import com.hot6.backend.board.comment.model.CommentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @PutMapping("/{idx}")
    public ResponseEntity<String> updateComment(@PathVariable Long idx,
                                                @RequestBody CommentDto.RegisterRequest updateRequest) {
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<String> deleteComment(@PathVariable Long idx) {
        return ResponseEntity.ok("ok");
    }
}
