package com.sparta.travel.global;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorResponse {
    private final HttpStatus status;
    private final String message;


    public ErrorResponse(ErrorCode errorCode) {
        this.status =errorCode.getHttpStatus();
        this.message = errorCode.getMessage();
    }
}
