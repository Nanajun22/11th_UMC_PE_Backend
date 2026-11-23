package com.example.umcbe.domain.favorite.dto;

import io.swagger.v3.oas.annotations.media.Schema;


public record FavoriteStatusResponse(
        @Schema(description = "즐겨찾기 여부 (true: 즐겨찾기함, false: 안 함)", example = "true")
        boolean isFavorite
) {
}