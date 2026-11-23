package com.example.umcbe.domain.favorite.controller;


import com.example.umcbe.domain.favorite.controller.docs.FavoriteControllerDocs;
import com.example.umcbe.domain.favorite.dto.CreateFavoriteRequest;
import com.example.umcbe.domain.favorite.dto.FavoriteResponse;
import com.example.umcbe.domain.favorite.dto.FavoriteStatusResponse;
import com.example.umcbe.domain.favorite.service.FavoriteService;
import com.example.umcbe.global.code.SuccessCode;
import com.example.umcbe.global.interceptor.AuthInterceptor;
import com.example.umcbe.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FavoriteController implements FavoriteControllerDocs {

    private final FavoriteService favoriteService;

    @PostMapping("/favorites")
    public ResponseEntity<ApiResponse<FavoriteResponse>> create(
            @RequestAttribute(AuthInterceptor.MEMBER_ID) Long memberId,
            @RequestBody @Valid CreateFavoriteRequest request
    ) {
        FavoriteResponse response = favoriteService.create(memberId, request);
        return ApiResponse.success(SuccessCode.COMMON_CREATED, response);
    }

    @GetMapping("/favorites")
    public ResponseEntity<ApiResponse<List<FavoriteResponse>>> getMyFavorites(
            @RequestAttribute(AuthInterceptor.MEMBER_ID) Long memberId
    ) {
        List<FavoriteResponse> response = favoriteService.getMyFavorites(memberId);
        return ApiResponse.success(SuccessCode.COMMON_OK, response);
    }

    @GetMapping("/favorites/{movieId}")
    public ResponseEntity<ApiResponse<FavoriteStatusResponse>> getStatus(
            @RequestAttribute(AuthInterceptor.MEMBER_ID) Long memberId,
            @PathVariable Long movieId
    ) {
        FavoriteStatusResponse response = favoriteService.getStatus(memberId, movieId);
        return ApiResponse.success(SuccessCode.COMMON_OK, response);
    }

    @DeleteMapping("/favorites/{movieId}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @RequestAttribute(AuthInterceptor.MEMBER_ID) Long memberId,
            @PathVariable Long movieId
    ) {
        favoriteService.delete(memberId, movieId);
        return ApiResponse.success(SuccessCode.COMMON_DELETED);
    }
}
