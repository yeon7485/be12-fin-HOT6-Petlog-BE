package com.hot6.backend.board.comment;

import com.hot6.backend.board.comment.model.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@CrossOrigin(origins = "http://localhost:5173")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody CommentDto.CommentRequest dto) {
        commentService.create(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list/{postIdx}")
    public ResponseEntity<List<CommentDto.CommentResponse>> list(@PathVariable Long postIdx) {
        return ResponseEntity.ok(commentService.list(postIdx));
    }
}

