package com.example.umcbe.domain.favorite.controller.docs;

import com.example.umcbe.domain.favorite.dto.CreateFavoriteRequest;
import com.example.umcbe.domain.favorite.dto.FavoriteResponse;
import com.example.umcbe.domain.favorite.dto.FavoriteStatusResponse;
import com.example.umcbe.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "즐겨찾기", description = "로그인한 회원의 영화 즐겨찾기 API")
public interface FavoriteControllerDocs {


    @Operation(
            summary = "즐겨찾기 등록",
            description = "토큰의 주인인 회원이 TMDB 영화 ID 하나를 즐겨찾기에 추가합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201", description = "즐겨찾기 등록 성공",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {
                              "isSuccess": true,
                              "code": "COMMON_201",
                              "message": "요청 리소스 생성 성공",
                              "data": { "favoriteId": 1, "movieId": 550, "createdAt": "2026-09-13T21:00:00" }
                            }
                            """))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401", description = "토큰이 없거나 유효하지 않음",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {
                              "isSuccess": false,
                              "code": "AUTH_401_2",
                              "message": "로그인이 필요한 요청입니다.",
                              "data": null
                            }
                            """))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "409", description = "이미 즐겨찾기한 영화",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {
                              "isSuccess": false,
                              "code": "FAVORITE_409_1",
                              "message": "이미 이 영화를 즐겨찾기하셨습니다.",
                              "data": null
                            }
                            """)))
    })
    ResponseEntity<ApiResponse<FavoriteResponse>> create(Long memberId, CreateFavoriteRequest request);

    @Operation(
            summary = "내 즐겨찾기 목록 조회",
            description = """
                    토큰의 주인인 회원이 즐겨찾기한 영화 목록을 최신순(favoriteId 내림차순)으로 조회합니다.
                    즐겨찾기가 없으면 빈 배열을 반환합니다.
                    토큰이 없거나 유효하지 않으면 401(AUTH_401_2, AUTH_401_3, AUTH_401_4)을 반환합니다.
                    """
    )
    ResponseEntity<ApiResponse<List<FavoriteResponse>>> getMyFavorites(Long memberId);

    @Operation(
            summary = "즐겨찾기 여부 조회",
            description = """
                    토큰의 주인인 회원이 해당 TMDB 영화 ID를 즐겨찾기했는지 여부를 조회합니다.
                    즐겨찾기하지 않은 영화라도 에러가 아닌 isFavorite: false 로 응답합니다.
                    토큰이 없거나 유효하지 않으면 401(AUTH_401_2, AUTH_401_3, AUTH_401_4)을 반환합니다.
                    """
    )
    ResponseEntity<ApiResponse<FavoriteStatusResponse>> getStatus(Long memberId, Long movieId);

    @Operation(
            summary = "즐겨찾기 삭제",
            description = """
                    토큰의 주인인 회원의 즐겨찾기에서 해당 TMDB 영화 ID를 삭제합니다. 성공 시 data는 null입니다.
                    즐겨찾기하지 않은 영화면 404(FAVORITE_404_1)를 반환합니다.
                    토큰이 없거나 유효하지 않으면 401(AUTH_401_2, AUTH_401_3, AUTH_401_4)을 반환합니다.
                    """
    )
    ResponseEntity<ApiResponse<Void>> delete(Long memberId, Long movieId);
}
