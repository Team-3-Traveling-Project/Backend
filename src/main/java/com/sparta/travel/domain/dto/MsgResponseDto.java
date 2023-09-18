package com.sparta.travel.domain.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MsgResponseDto {
    private String message;

    private int status;
    public MsgResponseDto(String message,int status) {
        this.message = message;
        this.status = status;
    }
}
