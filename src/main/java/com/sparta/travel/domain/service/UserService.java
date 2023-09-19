package com.sparta.travel.domain.service;

import com.sparta.travel.domain.dto.MsgResponseDto;
import com.sparta.travel.domain.dto.ProfileRequestDto;
import com.sparta.travel.domain.dto.SignupRequestDto;
import com.sparta.travel.domain.entity.Bookmark;
import com.sparta.travel.domain.entity.Plan;
import com.sparta.travel.domain.entity.User;
import com.sparta.travel.domain.jwt.UserRoleEnum;
import com.sparta.travel.domain.repository.BookmarkRepository;
import com.sparta.travel.domain.repository.PlanRepository;
import com.sparta.travel.domain.repository.UserRepository;
import com.sparta.travel.global.CustomException;
import com.sparta.travel.global.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BookmarkRepository bookmarkRepository;
    private final PlanRepository planRepository;

    public MsgResponseDto signup(SignupRequestDto requestDto) { // 프론트랑 합칠 시 변경
        String userId = requestDto.getUserId();
        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> checkUser = userRepository.findByUserId(userId); //ID 중복확인
        if(checkUser.isPresent()){
            throw new CustomException(ErrorCode.DUPLICATED_ID);
        }

        String email = requestDto.getEmail();
        checkEmail(email); // 이메일 중복확인

        String nickname = requestDto.getNickname();
        checkNickname(nickname); // 닉네임 중복확인

        UserRoleEnum role = UserRoleEnum.USER;

        User user = new User(userId,password,role,email,nickname);
        userRepository.save(user);
        return new MsgResponseDto(HttpServletResponse.SC_OK, "회원가입이 성공했습니다.");
    }

    public MsgResponseDto deleteUser(User user) {
        User deleteUser = checkUser(user);

        List<Bookmark> bookmarkList = bookmarkRepository.findByUser(deleteUser); // 연관된 북마크 삭제
        bookmarkRepository.deleteAll(bookmarkList);

        List<Plan> planList = planRepository.findByUser(deleteUser); // 연관된 일정 삭제
        planRepository.deleteAll(planList);

        userRepository.delete(deleteUser); // 유저 삭제
        return new MsgResponseDto(HttpServletResponse.SC_OK, "탈퇴가 완료됐습니다.");

    }

    public MsgResponseDto updateProfile(ProfileRequestDto requestDto, User user) {
        User updateUser = checkUser(user);
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();
        checkEmail(email); // 이메일 중복확인
        String nickname = requestDto.getNickname();
        checkNickname(nickname); // 닉네임 중복확인

        updateUser.update(password,email,nickname);

        return new MsgResponseDto(HttpServletResponse.SC_OK, "프로필수정이 성공했습니다.");
    }

    public User checkUser (User user) {
        return userRepository.findByUserId(user.getUserId()).
                orElseThrow(() -> new CustomException(ErrorCode.ID_NOT_FOUND));

    }

    public void checkEmail (String email) {

        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        }
    }

    public void checkNickname (String nickname) {

        Optional<User> checkNickname = userRepository.findByNickname(nickname);
        if (checkNickname.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATED_NICKNAME);
        }
    }

}

