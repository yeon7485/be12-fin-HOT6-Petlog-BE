package com.hot6.backend.board.answer;

import com.hot6.backend.board.answer.model.AnswerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/answer")
@CrossOrigin(origins = "http://localhost:5173")
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody AnswerDto.AnswerRequest dto) {
        answerService.create(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list/{questionIdx}")
    public ResponseEntity<List<AnswerDto.AnswerResponse>> list(@PathVariable Long questionIdx) {
        return ResponseEntity.ok(answerService.listByQuestion(questionIdx));
    }

    @PostMapping("/{idx}/select")
    public ResponseEntity<Void> select(@PathVariable Long idx) {
        answerService.select(idx);
        return ResponseEntity.ok().build();
    }
}

