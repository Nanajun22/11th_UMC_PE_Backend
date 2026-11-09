package com.example.umcbe.global.exception;

import com.example.umcbe.global.code.BaseCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final BaseCode baseCode;
    public CustomException(BaseCode basecode) {
        super(basecode.getMessage());
        this.baseCode = basecode;
    }
}
