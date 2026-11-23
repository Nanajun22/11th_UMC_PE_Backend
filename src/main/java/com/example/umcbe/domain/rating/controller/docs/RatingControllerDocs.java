package com.example.umcbe.domain.rating.controller.docs;

import com.example.umcbe.domain.rating.dto.CreateRatingRequest;
import com.example.umcbe.domain.rating.dto.RatingResponse;
import com.example.umcbe.domain.rating.dto.UpdateRatingRequest;
import com.example.umcbe.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "평점", description = "영화 평점 등록/조회/수정/삭제 API")
public interface RatingControllerDocs {

    @Operation(
            summary = "회원별 평점 목록 조회",
            description = """
                    특정 회원이 남긴 평점 목록을 최신순(ratingId 내림차순)으로 조회합니다. 토큰이 없어도 호출할 수 있는 공개 API입니다.
                    존재하지 않는 회원 ID면 404(MEMBER_404_1)를 반환합니다.
                    공통 응답(ApiResponse)으로 감싸지 않고 평점 배열을 바로 응답합니다.
                    """
    )
    @SecurityRequirements
    List<RatingResponse> getMemberRatings(Long memberId);

    @Operation(
            summary = "평점 등록",
            description = """
                    토큰의 주인인 회원이 TMDB 영화 ID에 별점(1~5)과 후기를 남깁니다. 성공 시 data로 생성된 ratingId를 반환합니다.
                    입력값이 올바르지 않으면 400(COMMON_400_1)을 반환합니다.
                    이미 같은 영화에 평점을 남겼으면 409(RATING_409_1)를 반환합니다.
                    토큰의 회원이 존재하지 않으면 404(MEMBER_404_1)를 반환합니다.
                    토큰이 없거나 유효하지 않으면 401(AUTH_401_2, AUTH_401_3, AUTH_401_4)을 반환합니다.
                    """
    )
    ResponseEntity<ApiResponse<Long>> create(Long memberId, CreateRatingRequest request);

    @Operation(
            summary = "평점 수정",
            description = """
                    내가 작성한 평점의 별점과 후기를 수정합니다. 성공 시 data는 null입니다.
                    입력값이 올바르지 않으면 400(COMMON_400_1)을 반환합니다.
                    존재하지 않는 평점이면 404(RATING_404_1), 다른 회원의 평점이면 403(RATING_403_1)을 반환합니다.
                    토큰이 없거나 유효하지 않으면 401(AUTH_401_2, AUTH_401_3, AUTH_401_4)을 반환합니다.
                    """
    )
    ResponseEntity<ApiResponse<Void>> update(Long memberId, Long ratingId, UpdateRatingRequest request);

    @Operation(
            summary = "평점 삭제",
            description = """
                    내가 작성한 평점을 삭제합니다. 성공 시 data는 null입니다.
                    존재하지 않는 평점이면 404(RATING_404_1), 다른 회원의 평점이면 403(RATING_403_1)을 반환합니다.
                    토큰이 없거나 유효하지 않으면 401(AUTH_401_2, AUTH_401_3, AUTH_401_4)을 반환합니다.
                    """
    )
    ResponseEntity<ApiResponse<Void>> delete(Long memberId, Long ratingId);
}
