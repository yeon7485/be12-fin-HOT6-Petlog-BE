package com.hot6.backend.board.post;

import com.hot6.backend.board.post.model.PostDto;
import com.hot6.backend.common.BaseResponse;
import com.hot6.backend.common.BaseResponseStatus;
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
    public ResponseEntity<BaseResponse<Void>> create(
            @RequestPart("post") PostDto.PostRequest dto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException {
        postService.create(dto, images);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS));
    }

    @GetMapping("/list/{boardName}")
    public ResponseEntity<BaseResponse<Page<PostDto.PostResponse>>> list(
            @PathVariable String boardName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS, postService.list(boardName, page, size)));
    }

    @GetMapping("/read/{postIdx}")
    public ResponseEntity<BaseResponse<PostDto.PostResponse>> read(@PathVariable Long postIdx) {
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS, postService.read(postIdx)));
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponse<Page<PostDto.PostResponse>>> search(
            @RequestParam String boardName,
            @RequestParam String category,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS,
                postService.search(boardName, category, keyword, page, size)));
    }

    @DeleteMapping("/delete/{idx}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long idx) {
        postService.delete(idx);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS));
    }

    @PutMapping(value = "/update/{idx}", consumes = {"multipart/form-data"})
    public ResponseEntity<BaseResponse<Void>> update(
            @PathVariable Long idx,
            @RequestPart("post") PostDto.PostRequest dto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException {
        postService.update(idx, dto, images);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS));
    }

    @GetMapping("/list/user/{userId}")
    public ResponseEntity<BaseResponse<List<PostDto.UserPostResponse>>> getUserPosts(@PathVariable Long userId) {
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS, postService.findUserPosts(userId)));
    }
}
