package com.example.umcbe.global.exception;

import com.example.umcbe.global.code.ErrorCode;
import com.example.umcbe.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e, HttpServletRequest request) {
        log.warn("[CustomException] {} {} - {}",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());

        return ApiResponse.failure(e.getBaseCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e, HttpServletRequest request) {

        Map<String, String> errors = new LinkedHashMap<>();
        for (FieldError fe : e.getBindingResult().getFieldErrors()) {
            errors.merge(fe.getField(), fe.getDefaultMessage(), (a, b) -> a + ", " + b);
        }

        log.warn("[Validation] {} {} - {}", request.getMethod(), request.getRequestURI(), errors);

        return ApiResponse.failure(ErrorCode.VALIDATION_ERROR, errors);
    }

}
