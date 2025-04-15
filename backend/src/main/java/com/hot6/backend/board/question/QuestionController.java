package com.hot6.backend.board.question;

import com.hot6.backend.board.question.model.QuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@RestController
@RequestMapping("/question")
public class    QuestionController {

    private final QuestionService questionService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody QuestionDto.QuestionRequest dto) {
        questionService.create(dto);
        return ResponseEntity.ok("성공");
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

    @PutMapping("/update/{idx}")
    public ResponseEntity<Void> update(@PathVariable Long idx, @RequestBody QuestionDto.QuestionRequest dto) {
        questionService.update(idx, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{idx}")
    public ResponseEntity<Void> delete(@PathVariable Long idx) {
        questionService.delete(idx);
        return ResponseEntity.ok().build();
    }

}
