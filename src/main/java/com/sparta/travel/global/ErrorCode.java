package com.sparta.travel.global;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    ID_NOT_MATCH(HttpStatus.BAD_REQUEST, "작성자가 일치하지 않습니다"),
    ID_NOT_FOUND(HttpStatus.NOT_FOUND, "아이디를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "선택한 댓글은 존재하지 않습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "선택한 게시글은 존재하지 않습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다."),
    DUPLICATED_ID(HttpStatus.BAD_REQUEST, "중복된 아이디입니다."),
    ADMINTOKEN_NOT_MATCH(HttpStatus.BAD_REQUEST, "관리자 패스워드가 일치하지 않습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "잘못된 패스워드입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }



    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
