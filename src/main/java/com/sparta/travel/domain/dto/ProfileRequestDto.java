package com.sparta.travel.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileRequestDto {
    @Size(min = 8, max = 15)
    @Pattern(regexp = "^[a-zA-Z0-9\\W]+$")
    private String password;

    @Email
    private String email;

    private String nickname;
}
