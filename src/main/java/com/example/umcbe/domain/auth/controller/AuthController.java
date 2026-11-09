package com.example.umcbe.domain.auth.controller;


import com.example.umcbe.domain.auth.dto.LoginRequest;
import com.example.umcbe.domain.auth.dto.LoginResponse;
import com.example.umcbe.domain.auth.service.AuthService;
import com.example.umcbe.global.code.SuccessCode;
import com.example.umcbe.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @RequestBody @Valid LoginRequest request
    ) {
        LoginResponse response = authService.login(request);
        return ApiResponse.success(SuccessCode.COMMON_OK, response);
    }
}
