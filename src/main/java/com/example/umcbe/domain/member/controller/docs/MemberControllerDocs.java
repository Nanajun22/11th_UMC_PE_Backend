package com.example.umcbe.domain.member.controller.docs;

import com.example.umcbe.domain.member.dto.EmailCheckResponse;
import com.example.umcbe.domain.member.dto.MemberInfoResponse;
import com.example.umcbe.domain.member.dto.MemberResponse;
import com.example.umcbe.domain.member.dto.MemberUpdateRequest;
import com.example.umcbe.domain.member.dto.NicknameCheckResponse;
import com.example.umcbe.domain.member.dto.SignUpRequest;
import com.example.umcbe.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;


@Tag(name = "회원", description = "회원가입과 내 정보 API")
public interface MemberControllerDocs {

    @Operation(
            summary = "닉네임 중복 확인",
            description = """
                    회원가입 전에 닉네임 사용 가능 여부를 확인합니다. 토큰이 없어도 호출할 수 있는 공개 API입니다.
                    사용 가능하면 available: true, 이미 사용 중이면 available: false 를 반환합니다.
                    공통 응답(ApiResponse)으로 감싸지 않고 { "available": true } 형태로 바로 응답합니다.
                    """
    )
    @SecurityRequirements
    NicknameCheckResponse checkNickname(String nickname);

    @Operation(
            summary = "이메일 중복 확인",
            description = """
                    회원가입 전에 이메일 사용 가능 여부를 확인합니다. 토큰이 없어도 호출할 수 있는 공개 API입니다.
                    사용 가능하면 available: true, 이미 사용 중이면 available: false 를 반환합니다.
                    공통 응답(ApiResponse)으로 감싸지 않고 { "available": true } 형태로 바로 응답합니다.
                    """
    )
    @SecurityRequirements
    EmailCheckResponse checkEmail(String email);

    @Operation(
            summary = "회원가입",
            description = "이메일, 비밀번호, 닉네임으로 회원을 등록합니다. 토큰이 없어도 호출할 수 있는 공개 API입니다."
    )
    @SecurityRequirements
    ResponseEntity<ApiResponse<MemberResponse>> signUp(SignUpRequest request);

    @Operation(
            summary = "내 정보 조회",
            description = """
                    토큰의 주인인 회원의 이메일, 닉네임, 가입일시를 조회합니다.
                    토큰의 회원이 존재하지 않으면 404(MEMBER_404_1)를 반환합니다.
                    토큰이 없거나 유효하지 않으면 401(AUTH_401_2, AUTH_401_3, AUTH_401_4)을 반환합니다.
                    """
    )
    ResponseEntity<ApiResponse<MemberInfoResponse>> getMyInfo(Long memberId);

    @Operation(
            summary = "내 정보 수정",
            description = """
                    토큰의 주인인 회원의 닉네임을 수정하고, 수정된 내 정보를 반환합니다.
                    닉네임이 비어 있거나 2~12자가 아니면 400(COMMON_400_1)을 반환합니다.
                    다른 회원이 이미 사용 중인 닉네임이면 409(MEMBER_409_2)를 반환합니다. (현재 내 닉네임과 같은 값은 허용)
                    토큰의 회원이 존재하지 않으면 404(MEMBER_404_1)를 반환합니다.
                    토큰이 없거나 유효하지 않으면 401(AUTH_401_2, AUTH_401_3, AUTH_401_4)을 반환합니다.
                    """
    )
    ResponseEntity<ApiResponse<MemberInfoResponse>> updateMyInfo(Long memberId, MemberUpdateRequest request);
}
