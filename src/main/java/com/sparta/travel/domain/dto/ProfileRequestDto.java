package com.sparta.travel.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileRequestDto {
    private String password;
    private String email;
    private String nickname;
}
