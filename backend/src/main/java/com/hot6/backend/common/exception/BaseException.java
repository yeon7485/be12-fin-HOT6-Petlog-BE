package com.hot6.backend.common.exception;

import com.hot6.backend.common.BaseResponseStatus;
import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;

@Getter
@Setter
public class BaseException extends RuntimeException {
    private final BaseResponseStatus status;
    public BaseException(BaseResponseStatus status) {
        super(status.getMessage());
        this.status = status;
    }
    public BaseException(BaseResponseStatus status, Object... args) {
        super(MessageFormat.format(status.getMessage(), args));
        this.status = status;
    }

    public BaseResponseStatus getStatus() {
        return status;
    }
}
