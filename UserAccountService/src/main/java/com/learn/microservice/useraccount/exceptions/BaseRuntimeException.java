package com.learn.microservice.useraccount.exceptions;

import com.learn.microservice.useraccount.exceptions.error.ErrorCode;
import lombok.Getter;
import org.springframework.context.support.MessageSourceAccessor;

public class BaseRuntimeException extends RuntimeException {

    @Getter
    private final ErrorCode errorCode;

    @Getter
    private final String errorMessage;

    public BaseRuntimeException(ErrorCode errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static BaseRuntimeException createWithErrorCode(ErrorCode errorCode, MessageSourceAccessor messageSourceAccessor) {
        String errorMessage = messageSourceAccessor.getMessage(errorCode.toString());
        return new BaseRuntimeException(errorCode, errorMessage);
    }
}
