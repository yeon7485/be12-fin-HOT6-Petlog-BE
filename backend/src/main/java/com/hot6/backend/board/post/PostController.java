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

    @DeleteMapping("/delete/{idx}")
    public ResponseEntity<Void> delete(@PathVariable Long idx) {
        postService.delete(idx);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{idx}")
    public ResponseEntity<Void> update(@PathVariable Long idx, @RequestBody PostDto.PostRequest dto) {
        postService.update(idx, dto);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/list/user/{userId}")
    public ResponseEntity<List<PostDto.UserPostResponse>> getUserPosts(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.findUserPosts(userId));
    }
}