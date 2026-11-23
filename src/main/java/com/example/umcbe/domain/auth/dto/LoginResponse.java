package com.example.umcbe.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;


public record LoginResponse(
        @Schema(description = "로그인한 회원의 닉네임", example = "영화조아")
        String nickname,
        @Schema(description = "API 요청 시 Authorization: Bearer {accessToken} 으로 사용하는 JWT", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIn0.abc123")
        String accessToken
) {
}
