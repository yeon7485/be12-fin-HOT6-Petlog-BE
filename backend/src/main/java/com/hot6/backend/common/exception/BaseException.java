package com.hot6.backend.common.exception;

import com.hot6.backend.common.BaseResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends RuntimeException {
    private final BaseResponseStatus status;
    public BaseException(BaseResponseStatus status) {
        super(status.getMessage());
        this.status = status;
    }
}
