package com.example.umcbe.domain.member.dto;

import com.example.umcbe.domain.member.domain.Member;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

public record MemberInfoResponse(
        @Schema(description = "회원 이메일", example = "challenger@umc.com")
        String email,
        @Schema(description = "회원 닉네임", example = "영화조아")
        String nickname,
        @Schema(description = "가입 일시", example = "2026-09-13T21:00:00")
        LocalDateTime createdAt
) {
    public static MemberInfoResponse from(Member member) {
        return new MemberInfoResponse(
                member.getEmail(),
                member.getNickname(),
                member.getCreatedAt()
        );
    }
}
