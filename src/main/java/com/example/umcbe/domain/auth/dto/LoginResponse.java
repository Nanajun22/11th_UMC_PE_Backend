package com.example.umcbe.domain.auth.dto;


public record LoginResponse(
        String nickname,
        String accessToken
) {
}
