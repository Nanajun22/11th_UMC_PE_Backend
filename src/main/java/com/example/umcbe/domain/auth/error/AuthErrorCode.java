package com.example.umcbe.domain.auth.error;

import com.example.umcbe.global.code.BaseCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum AuthErrorCode implements BaseCode {

    LOGIN_FAILED(HttpStatus.UNAUTHORIZED,"AUTH_401_1","이메일 또는 비밀번호가 올바르지 않습니다."),
    NO_TOKEN(HttpStatus.UNAUTHORIZED,"AUTH_401_2","로그인이 필요한 요청입니다."),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED,"AUTH_401_3","유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED,"AUTH_401_4","토큰이 만료되었습니다. 다시 로그인해주세요.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
