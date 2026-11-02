package com.example.umcbe.global.exception;

import com.example.umcbe.global.code.BaseCode;
import lombok.Getter;

@Getter
public class CustomExeption extends RuntimeException {
    private BaseCode baseCode;
    public CustomExeption(BaseCode basecode) {
        super(basecode.getMessage());
    }
}
