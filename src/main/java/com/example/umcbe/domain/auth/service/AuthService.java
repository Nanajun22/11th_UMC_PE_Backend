package com.example.umcbe.domain.auth.service;

import com.example.umcbe.domain.auth.dto.LoginRequest;
import com.example.umcbe.domain.auth.dto.LoginResponse;
import com.example.umcbe.domain.auth.error.AuthErrorCode;
import com.example.umcbe.domain.member.domain.Member;
import com.example.umcbe.domain.member.repository.MemberRepository;
import com.example.umcbe.global.exception.CustomException;
import com.example.umcbe.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request) {

        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new CustomException(AuthErrorCode.LOGIN_FAILED));


        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new CustomException(AuthErrorCode.LOGIN_FAILED);
        }


        String accessToken = jwtUtil.createAccessToken(member.getMemberId(), member.getEmail());

        return new LoginResponse(member.getNickname(), accessToken);
    }
}