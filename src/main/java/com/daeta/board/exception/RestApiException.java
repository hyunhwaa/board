package com.daeta.board.exception;

import com.daeta.board.entity.enumSet.ErrorType;
import lombok.Getter;

@Getter
public class RestApiException extends RuntimeException {
    private final ErrorType errorType;

    public RestApiException(ErrorType errorType){
        this.errorType = errorType;
    }
}
