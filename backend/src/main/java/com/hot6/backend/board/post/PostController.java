package com.hot6.backend.board.post;

import com.hot6.backend.board.post.model.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@CrossOrigin(origins = "http://localhost:5173")
public class PostController {

    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody PostDto.PostRequest dto) {
        postService.create(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list/{boardName}")
    public ResponseEntity<List<PostDto.PostResponse>> list(@PathVariable String boardName) {
        return ResponseEntity.ok(postService.list(boardName));
    }

    @GetMapping("/read/{idx}")
    public ResponseEntity<PostDto.PostResponse> read(@PathVariable Long idx) {
        return ResponseEntity.ok(postService.read(idx));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostDto.PostResponse>> search(
            @RequestParam String boardName,
            @RequestParam String category,
            @RequestParam(required = false) String keyword
    ) {
        return ResponseEntity.ok(postService.search(boardName, category, keyword));
    }
}