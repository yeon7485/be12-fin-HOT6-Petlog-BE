package com.hot6.backend.board.question;

import com.hot6.backend.board.post.model.PostDto;
import com.hot6.backend.board.question.model.QuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    public ResponseEntity<String> create(
            @RequestPart("question") QuestionDto.QuestionRequest dto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) throws IOException {
        questionService.create(dto, images);
        return ResponseEntity.ok("질문 등록 성공");
    }

    @PutMapping(value = "/update/{idx}", consumes = {"multipart/form-data"})
    public ResponseEntity<Void> update(
            @PathVariable Long idx,
            @RequestPart("question") QuestionDto.QuestionRequest dto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) throws IOException {
        questionService.update(idx, dto, images);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<QuestionDto.QuestionResponse>> list() {
        return ResponseEntity.ok(questionService.list());
    }

    @GetMapping("/search")
    public ResponseEntity<List<QuestionDto.QuestionResponse>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(questionService.search(keyword));
    }

    @GetMapping("/read/{idx}")
    public ResponseEntity<QuestionDto.QuestionResponse> read(@PathVariable Long idx) {
        QuestionDto.QuestionResponse response = questionService.read(idx);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{idx}")
    public ResponseEntity<Void> delete(@PathVariable Long idx) {
        questionService.delete(idx);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list/user/{userId}")
    public ResponseEntity<List<QuestionDto.UserQuestionResponse>> getUserQuestions(@PathVariable Long userId) {
        return ResponseEntity.ok(questionService.findUserQuestions(userId));
    }
}