package com.sparta.travel.domain.service;

import com.sparta.travel.domain.dto.MsgResponseDto;
import com.sparta.travel.domain.dto.ProfileRequestDto;
import com.sparta.travel.domain.dto.SignupRequestDto;
import com.sparta.travel.domain.entity.User;
import com.sparta.travel.domain.jwt.UserRoleEnum;
import com.sparta.travel.domain.repository.UserRepository;
import com.sparta.travel.global.CustomException;
import com.sparta.travel.global.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public MsgResponseDto signup(SignupRequestDto requestDto) { // 프론트랑 합칠 시 변경
        String userId = requestDto.getUserId();
        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> checkUser = userRepository.findByUserId(userId); //ID 중복확인
        if(checkUser.isPresent()){
            throw new CustomException(ErrorCode.DUPLICATED_ID);
        }

        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email); // Email 중복 확인
        if (checkEmail.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        }

        String nickname = requestDto.getNickname();
        Optional<User> checkNickname = userRepository.findByNickname(nickname); // 닉네임 중복 확인
        if (checkNickname.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATED_NICKNAME);
        }

        UserRoleEnum role = UserRoleEnum.USER;
        User user = new User(userId,password,role,email,nickname);
        userRepository.save(user);
        return new MsgResponseDto(HttpServletResponse.SC_OK, "회원가입 성공");
    }

    public MsgResponseDto deleteUser(User user) {
        Optional<User> checkUser = userRepository.findByUserId(user.getUserId());
        if (checkUser.isEmpty()) {
            throw new CustomException(ErrorCode.ID_NOT_FOUND);
        }
        userRepository.delete(user);
        return new MsgResponseDto(HttpServletResponse.SC_OK, "회원탈퇴 성공");

    }
    public MsgResponseDto updateProfile(ProfileRequestDto requestDto, User user) {
        boolean chkUser = userRepository.findAllByUserId(user.getUserId());
        if (chkUser) {
            user.update(requestDto);
        } else {
            throw new CustomException(ErrorCode.ID_NOT_FOUND);
        }

        return new MsgResponseDto(HttpServletResponse.SC_OK, "프로필수정이 성공했습니다.");
    }
}

