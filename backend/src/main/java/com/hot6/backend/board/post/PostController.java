package com.hot6.backend.board.post;

import com.hot6.backend.board.post.model.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@CrossOrigin(origins = "http://localhost:5173")
public class PostController {

    private final PostService postService;

    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    public ResponseEntity<Void> create(
            @RequestPart("post") PostDto.PostRequest dto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) throws IOException {
        postService.create(dto, images);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list/{boardName}")
    public ResponseEntity<Page<PostDto.PostResponse>> list(
            @PathVariable String boardName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<PostDto.PostResponse> responses = postService.list(boardName, page, size);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/read/{postIdx}")
    public ResponseEntity<PostDto.PostResponse> read(@PathVariable Long postIdx) {
        PostDto.PostResponse response = postService.read(postIdx);
        return ResponseEntity.ok(response);
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

    @PutMapping(value = "/update/{idx}", consumes = {"multipart/form-data"})
    public ResponseEntity<Void> update(
            @PathVariable Long idx,
            @RequestPart("post") PostDto.PostRequest dto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) throws IOException {
        postService.update(idx, dto, images);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list/user/{userId}")
    public ResponseEntity<List<PostDto.UserPostResponse>> getUserPosts(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.findUserPosts(userId));
    }
}