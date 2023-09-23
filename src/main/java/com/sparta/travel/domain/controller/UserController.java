package com.sparta.travel.domain.controller;

import com.sparta.travel.domain.dto.*;
import com.sparta.travel.domain.security.UserDetailsImpl;
import com.sparta.travel.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


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

    @GetMapping("/user/updateprofile")
    public ProfileResponseDto getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getProfile(userDetails.getUser());
    }


    @PutMapping("/user/updateprofile")
    public ResponseEntity<MsgResponseDto> updateProfile(@Valid @RequestBody ProfileRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(userService.updateProfile(requestDto, userDetails.getUser()));
    }

    @PostMapping("/user/updateImg")
    public ProfileImgResponseDto updateProfileImg(@RequestParam(value = "image") MultipartFile image, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return userService.updateProfileImg(image, userDetails.getUser());
    }
}
