package com.example.umcbe.domain.member.dto;

public record MemberResponse(
        Long memberId,
        String email,
        String nickname
) {}