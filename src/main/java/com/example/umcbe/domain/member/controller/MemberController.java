package com.example.umcbe.domain.member.controller;

import com.example.umcbe.domain.member.dto.*;
import com.example.umcbe.domain.member.service.MemberService;
import com.example.umcbe.global.code.SuccessCode;
import com.example.umcbe.global.interceptor.AuthInterceptor;
import com.example.umcbe.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/nickname/{nickname}")
    public NicknameCheckResponse checkNickname(@PathVariable String nickname) {
        return memberService.checkNickname(nickname);
    }

    @GetMapping("/email/{email}")
    public EmailCheckResponse checkEmail(@PathVariable String email) {
        return memberService.checkEmail(email);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MemberResponse>> signUp(
            @Valid @RequestBody SignUpRequest request
    ) {
        MemberResponse response = memberService.signUp(request);
        return ApiResponse.success(SuccessCode.COMMON_CREATED,response);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MemberInfoResponse>> getMyInfo(
            @RequestAttribute(AuthInterceptor.MEMBER_ID) Long memberId
    ) {
        return ApiResponse.success(SuccessCode.COMMON_OK, memberService.getMyInfo(memberId));
    }

    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<MemberInfoResponse>> updateMyInfo(
            @RequestAttribute(AuthInterceptor.MEMBER_ID) Long memberId,
            @RequestBody @Valid MemberUpdateRequest request
    ) {
        return ApiResponse.success(SuccessCode.COMMON_OK, memberService.updateMyInfo(memberId, request));
    }
}