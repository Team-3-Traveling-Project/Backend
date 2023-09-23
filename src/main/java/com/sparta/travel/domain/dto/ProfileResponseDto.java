package com.sparta.travel.domain.dto;

import com.sparta.travel.domain.entity.User;
import lombok.Getter;

@Getter
public class ProfileResponseDto {
    private String email;
    private String nickname;
    private String profile_img_url;

    public ProfileResponseDto(User getUser) {
        this.email = getUser.getEmail();
        this.nickname = getUser.getNickname();
        this.profile_img_url = getUser.getProfile_img_url();
    }
}
