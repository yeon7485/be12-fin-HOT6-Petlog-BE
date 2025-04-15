package com.hot6.backend.board.hashtagQuestion;

import com.hot6.backend.board.hashtagQuestion.model.Hashtag_QuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hashtag")
public class Hashtag_QuestionController {

    private final Hashtag_QuestionService service;

    @PostMapping("/save")
    public void saveTags(@RequestBody Hashtag_QuestionDto.Request request) {
        service.saveTags(request.getTags(), request.getQuestionIdx());
    }

    @GetMapping("/question/{questionIdx}")
    public List<Hashtag_QuestionDto.Response> getTagsByQuestion(@PathVariable Long questionIdx) {
        return service.getTagsByQuestion(questionIdx);
    }
}
