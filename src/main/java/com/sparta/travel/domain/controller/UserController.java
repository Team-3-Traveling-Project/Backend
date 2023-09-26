package com.sparta.travel.domain.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.travel.domain.dto.*;
import com.sparta.travel.domain.jwt.JwtUtil;
import com.sparta.travel.domain.security.UserDetailsImpl;
import com.sparta.travel.domain.service.KakaoLoginService;
import com.sparta.travel.domain.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
@RequiredArgsConstructor
//@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final KakaoLoginService kakaoLoginService;
   

    @PostMapping("/api/user/signup") //front랑 합칠 시 추후 변경
    @ResponseBody
    public ResponseEntity<MsgResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        return ResponseEntity.ok(userService.signup(requestDto));
    }

    @DeleteMapping("/api/user/userdel")
    @ResponseBody
    public ResponseEntity<MsgResponseDto> deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.ok(userService.deleteUser(userDetails.getUser()));
    }

    @GetMapping("/api/user/updateprofile")
    @ResponseBody
    public ProfileResponseDto getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getProfile(userDetails.getUser());
    }


    @PutMapping("/api/user/updateprofile")
    @ResponseBody
    public ResponseEntity<MsgResponseDto> updateProfile(@Valid @RequestBody ProfileRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(userService.updateProfile(requestDto, userDetails.getUser()));
    }

    @PostMapping("/api/user/updateImg")
    @ResponseBody
    public ProfileImgResponseDto updateProfileImg(@RequestParam(value = "image") MultipartFile image, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return userService.updateProfileImg(image, userDetails.getUser());
    }

    @GetMapping("/api/user/kakao/callback")
    @CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "Authorization")
    public ResponseEntity<Void> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {

        String token = kakaoLoginService.kakaoLogin(code);
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtUtil.AUTHORIZATION_HEADER, token);

        // 메인 페이지의 URL로 리다이렉트 응답을 반환
        String mainPageUrl = "http://localhost:3000/"; // 메인 페이지의 URL
        headers.add("Location", mainPageUrl);
        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }

}
