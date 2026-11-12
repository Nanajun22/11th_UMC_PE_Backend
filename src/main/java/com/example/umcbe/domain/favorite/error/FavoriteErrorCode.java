package com.example.umcbe.domain.favorite.error;

import com.example.umcbe.global.code.BaseCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum FavoriteErrorCode implements BaseCode {
    FAVORITE_NOT_FOUND(HttpStatus.NOT_FOUND,"FAVORITE_404_1","즐겨찾기한 영화를 찾을 수 없습니다."),
    FAVORITE_ALREADY_EXIST(HttpStatus.CONFLICT,"FAVORITE_409_1","이미 이 영화를 즐겨찾기하셨습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

}
