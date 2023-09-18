package com.sparta.travel.domain.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MsgResponseDto {
    private int statusCode;
    private String msg;

    public MsgResponseDto(int statusCode, String msg){
        this.statusCode = statusCode;
        this.msg = msg;
    }
}
