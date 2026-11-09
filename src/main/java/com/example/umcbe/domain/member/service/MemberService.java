package com.example.umcbe.domain.member.service;

import com.example.umcbe.domain.member.domain.Member;
import com.example.umcbe.domain.member.dto.*;
import com.example.umcbe.domain.member.error.MemberErrorCode;
import com.example.umcbe.domain.member.repository.MemberRepository;
import com.example.umcbe.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public NicknameCheckResponse checkNickname(String nickname) {
        boolean exists = memberRepository.existsByNickname(nickname);
        return new NicknameCheckResponse(!exists);
    }

    @Transactional(readOnly = true)
    public EmailCheckResponse checkEmail(String email) {
        boolean exists = memberRepository.existsByEmail(email);
        return new EmailCheckResponse(!exists);
    }

    @Transactional
    public MemberResponse signUp(SignUpRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new CustomException(MemberErrorCode.EMAIL_ALREADY_EXIST);
        }
        if (memberRepository.existsByNickname(request.nickname())) {
            throw new CustomException(MemberErrorCode.NICKNAME_ALREADY_EXIST);
        }

        Member member = Member.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .nickname(request.nickname())
                .build();

        Member savedMember = memberRepository.save(member);
        return new MemberResponse(savedMember.getMemberId(), savedMember.getEmail(), savedMember.getNickname());
    }

    public MemberInfoResponse getMyInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MemberErrorCode.MEMBER_NOT_FOUND));

        return MemberInfoResponse.from(member);
    }

    public MemberInfoResponse updateMyInfo(Long memberId, MemberUpdateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (memberRepository.existsByNicknameAndMemberIdNot(request.nickname(), memberId)) {
            throw new CustomException(MemberErrorCode.NICKNAME_ALREADY_EXIST);
        }

        member.updateNickname(request.nickname());
        memberRepository.save(member);

        return MemberInfoResponse.from(member);
    }
}