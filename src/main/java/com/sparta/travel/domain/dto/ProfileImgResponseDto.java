package com.sparta.travel.domain.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ProfileImgResponseDto {
    private int statusCode;
    private String msg;
    private String  profile_img_url;

    public ProfileImgResponseDto(int statusCode, String msg, String fileName) {
        this.statusCode = statusCode;
        this.msg = msg;
        this.profile_img_url = fileName;
    }
}
