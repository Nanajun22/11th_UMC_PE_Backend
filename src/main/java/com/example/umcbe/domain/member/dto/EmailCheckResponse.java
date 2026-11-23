package com.example.umcbe.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;


public record EmailCheckResponse(
        @Schema(description = "이메일 사용 가능 여부 (true: 사용 가능, false: 이미 사용 중)", example = "true")
        boolean available
) {}