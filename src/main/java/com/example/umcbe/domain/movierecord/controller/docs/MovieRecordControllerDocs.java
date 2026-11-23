package com.example.umcbe.domain.movierecord.controller.docs;

import com.example.umcbe.domain.movierecord.dto.CreateMovieRecordRequest;
import com.example.umcbe.domain.movierecord.dto.MovieRecordResponse;
import com.example.umcbe.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "영화 기록", description = "평점 등록과 즐겨찾기 추가를 한 번에 처리하는 API")
public interface MovieRecordControllerDocs {

    @Operation(
            summary = "영화 기록 등록",
            description = """
                    토큰의 주인인 회원이 TMDB 영화 ID에 평점을 등록하고, addToFavorites가 true면 즐겨찾기도 함께 추가합니다.
                    평점과 즐겨찾기는 하나의 트랜잭션으로 처리되어 하나라도 실패하면 모두 저장되지 않습니다.
                    addToFavorites가 false면 응답의 favorite은 null입니다.
                    입력값이 올바르지 않으면 400(COMMON_400_1)을 반환합니다.
                    이미 같은 영화에 평점을 남겼으면 409(RATING_409_1)를 반환합니다.
                    addToFavorites가 true인데 이미 즐겨찾기한 영화면 409(FAVORITE_409_1)를 반환합니다.
                    토큰의 회원이 존재하지 않으면 404(MEMBER_404_1)를 반환합니다.
                    토큰이 없거나 유효하지 않으면 401(AUTH_401_2, AUTH_401_3, AUTH_401_4)을 반환합니다.
                    """
    )
    ResponseEntity<ApiResponse<MovieRecordResponse>> create(Long memberId, CreateMovieRecordRequest request);
}
