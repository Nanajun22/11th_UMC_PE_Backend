package com.example.umcbe.domain.rating.errror;

import com.example.umcbe.global.code.BaseCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum RatingErrorCode implements BaseCode {

    NOT_MY_RATING(HttpStatus.FORBIDDEN,"RATING_403_1","본인이 작성한 평점만 수정하거나 삭제할 수 있습니다."),
    RATING_NOT_FOUND(HttpStatus.NOT_FOUND,"RATING_404_1","존재하지 않는 평점입니다."),
    ALREADY_RATED(HttpStatus.CONFLICT,"RATING_409_1","이미 이 영화에 평점을 남기셨습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
