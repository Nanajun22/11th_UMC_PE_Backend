package com.example.umcbe.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record MemberResponse(
        @Schema(description = "가입된 회원 ID", example = "1")
        Long memberId,
        @Schema(description = "가입된 회원 이메일", example = "challenger@umc.com")
        String email,
        @Schema(description = "가입된 회원 닉네임", example = "영화조아")
        String nickname
) {}