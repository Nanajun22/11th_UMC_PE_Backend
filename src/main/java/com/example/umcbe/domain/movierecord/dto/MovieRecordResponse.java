package com.example.umcbe.domain.movierecord.dto;


import com.example.umcbe.domain.favorite.dto.FavoriteResponse;
import com.example.umcbe.domain.rating.dto.RatingResponse;
import io.swagger.v3.oas.annotations.media.Schema;

public record MovieRecordResponse(
        @Schema(description = "등록된 평점 정보")
        RatingResponse rating,
        @Schema(description = "등록된 즐겨찾기 정보 (addToFavorites가 false면 null)", nullable = true)
        FavoriteResponse favorite
) {
}