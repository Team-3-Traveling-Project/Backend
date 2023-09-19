package com.sparta.travel.domain.controller;

import com.sparta.travel.domain.dto.MsgResponseDto;
import com.sparta.travel.domain.dto.SignupRequestDto;
import com.sparta.travel.domain.security.UserDetailsImpl;
import com.sparta.travel.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.sparta.travel.domain.dto.ProfileRequestDto;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup") //front랑 합칠 시 추후 변경
    public ResponseEntity<MsgResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        return ResponseEntity.ok(userService.signup(requestDto));
    }

    @DeleteMapping("/user/userdel")
    public ResponseEntity<MsgResponseDto> deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(userService.deleteUser(userDetails.getUser()));
    }

    @PutMapping("/user/updateprofile")
    public ResponseEntity<MsgResponseDto> updateProfile(@Valid @RequestBody ProfileRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(userService.updateProfile(requestDto, userDetails.getUser()));
    }
}
