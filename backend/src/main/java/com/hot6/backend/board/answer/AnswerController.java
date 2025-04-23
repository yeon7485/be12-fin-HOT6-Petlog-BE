package com.hot6.backend.board.answer;

import com.hot6.backend.board.answer.model.AnswerDto;
import com.hot6.backend.common.BaseResponse;
import com.hot6.backend.common.BaseResponseStatus;
import com.hot6.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/answer")
@CrossOrigin(origins = "http://localhost:5173")
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<String>> create(
            @AuthenticationPrincipal User currentUser,
            @RequestPart("answer") AnswerDto.AnswerRequest request,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException {
        answerService.create(currentUser, request, images);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS));
    }

    @GetMapping("/list/{questionIdx}")
    public ResponseEntity<BaseResponse<List<AnswerDto.AnswerResponse>>> list(@PathVariable Long questionIdx) {
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS, answerService.listByQuestion(questionIdx)));
    }

    @PostMapping("/{idx}/select")
    public ResponseEntity<BaseResponse<Void>> select(@PathVariable Long idx) {
        answerService.select(idx);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS));
    }

    @PutMapping(value = "/update/{idx}", consumes = {"multipart/form-data"})
    public ResponseEntity<BaseResponse<Void>> update(
            @PathVariable Long idx,
            @AuthenticationPrincipal User currentUser,
            @RequestPart("answer") AnswerDto.AnswerRequest dto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException {
        answerService.update(idx, currentUser, dto, images);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS));
    }

    @GetMapping("/read/{idx}")
    public ResponseEntity<BaseResponse<AnswerDto.AnswerResponse>> read(@PathVariable Long idx) {
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS, answerService.read(idx)));
    }

    @DeleteMapping("/delete/{idx}")
    public ResponseEntity<BaseResponse<Void>> deleteAnswer(
            @PathVariable Long idx,
            @AuthenticationPrincipal User currentUser) {
        answerService.delete(idx, currentUser);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS));
    }

    @GetMapping("/list/user/{userId}")
    public ResponseEntity<BaseResponse<List<AnswerDto.AnswerResponse>>> listByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseStatus.SUCCESS, answerService.readByAnswer(userId)));
    }
}
