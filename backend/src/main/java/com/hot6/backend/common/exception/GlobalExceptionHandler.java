package com.hot6.backend.common.exception;


import com.hot6.backend.common.BaseResponse;
import com.hot6.backend.common.BaseResponseStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse<BaseResponseStatus>> handleGlobalException(BaseException e) {

        return ResponseEntity.badRequest().body(new BaseResponse<>(e.getStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleValidationException(MethodArgumentNotValidException ex) {
        String field = ex.getBindingResult().getFieldErrors().get(0).getField();

        if ("title".equals(field)) {
            throw new BaseException(BaseResponseStatus.POST_REQUIRED_TITLE);
        } else if ("content".equals(field)) {
            throw new BaseException(BaseResponseStatus.POST_REQUIRED_CONTENT);
        }

        throw new BaseException(BaseResponseStatus.POST_CREATE_FAILED);
    }
}
