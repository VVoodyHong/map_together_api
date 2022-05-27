package com.mapto.api.common.model;

import lombok.Getter;

@Getter
public class CustomException extends Exception {
    private final StatusCode code;
    public CustomException(StatusCode code) {
        super();
        this.code = code;
    }
    public CustomException(String message, Throwable cause, StatusCode code) {
        super(message, cause);
        this.code = code;
    }
    public CustomException(String message, StatusCode code) {
        super(message);
        this.code = code;
    }
    public CustomException(Throwable cause, StatusCode code) {
        super(cause);
        this.code = code;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}

