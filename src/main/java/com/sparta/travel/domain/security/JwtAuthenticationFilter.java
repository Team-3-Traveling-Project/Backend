package com.sparta.travel.domain.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.travel.domain.dto.LoginRequestDto;
import com.sparta.travel.domain.dto.MsgResponseDto;
import com.sparta.travel.domain.jwt.JwtUtil;
import com.sparta.travel.domain.jwt.UserRoleEnum;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/user/login");
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUserId(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
    @SneakyThrows
    @Override //추가 메시지 작업
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String userId = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String token = jwtUtil.createToken(userId, role);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        response.setStatus(HttpServletResponse.SC_OK); // 상태코드 반환
        response.setContentType("application/json;charset=UTF-8"); // JSON 형식으로 반환

        MsgResponseDto responseBody = new MsgResponseDto("로그인 성공", HttpServletResponse.SC_OK);

        ObjectMapper objectMapper = new ObjectMapper(); // JSON 문자열로 바꿈
        String responseBodyToJson = objectMapper.writeValueAsString(responseBody);
        response.getWriter().write(responseBodyToJson);
    }

    @SneakyThrows
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8"); // JSON 형식으로 반환

        MsgResponseDto responseBody = new MsgResponseDto("로그인 실패", HttpServletResponse.SC_UNAUTHORIZED);

        ObjectMapper objectMapper = new ObjectMapper(); // JSON 문자열로 바꿈
        String responseBodyToJson = objectMapper.writeValueAsString(responseBody);
        response.getWriter().write(responseBodyToJson);

    }

}
