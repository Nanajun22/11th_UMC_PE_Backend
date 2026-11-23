package com.example.umcbe.domain.auth.controller.docs;


import com.example.umcbe.domain.auth.dto.LoginRequest;
import com.example.umcbe.domain.auth.dto.LoginResponse;
import com.example.umcbe.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "인증", description = "로그인과 토큰 발급 API")
public interface AuthControllerDocs {

    @Operation(
            summary = "이메일 로그인",
            description = """
                    이메일과 비밀번호로 로그인하고 Access Token을 발급받습니다.
                    발급받은 토큰은 위쪽 Authorize 버튼에 넣어두면 인증이 필요한 API에 자동으로 실립니다.
                    """
    )
    @SecurityRequirements
    ResponseEntity<ApiResponse<LoginResponse>> login(LoginRequest request);
}