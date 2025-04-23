package com.hot6.backend.board.hashtagQuestion;

import com.hot6.backend.board.hashtagQuestion.model.Hashtag_QuestionDto;
import com.hot6.backend.common.BaseResponse;
import com.hot6.backend.common.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hashtag")
public class Hashtag_QuestionController {

    private final Hashtag_QuestionService service;

    @PostMapping("/save")
    public BaseResponse<Void> saveTags(@RequestBody Hashtag_QuestionDto.Request request) {
        service.saveTags(request.getTags(), request.getQuestionIdx());
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @GetMapping("/question/{questionIdx}")
    public BaseResponse<List<Hashtag_QuestionDto.Response>> getTagsByQuestion(@PathVariable Long questionIdx) {
        List<Hashtag_QuestionDto.Response> tags = service.getTagsByQuestion(questionIdx);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS, tags);
    }
}
