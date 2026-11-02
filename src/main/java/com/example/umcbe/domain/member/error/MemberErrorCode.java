package com.example.umcbe.domain.member.error;

import com.example.umcbe.global.code.BaseCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum MemberErrorCode implements BaseCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND,"MEMBER_404_1","존재하지 않는 사용자입니다."),
    EMAIL_ALREADY_EXIST(HttpStatus.CONFLICT,"MEMBER_409_1","이미 사용 중인 이메일입니다."),
    NICKNAME_ALREADY_EXIST(HttpStatus.CONFLICT,"MEMBER_409_2","이미 사용 중인 닉네임입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

}
