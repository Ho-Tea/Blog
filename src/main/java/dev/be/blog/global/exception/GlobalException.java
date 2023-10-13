package dev.be.blog.global.exception;

import dev.be.blog.global.common.response.ResponseCode;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {

    private final ResponseCode.ErrorCode errorCode;

    public GlobalException(ResponseCode.ErrorCode errorCode) {
        super(errorCode.getMESSAGE());
        this.errorCode = errorCode;
    }
}
