package com.example.umcbe.domain.favorite.dto;

import com.example.umcbe.domain.favorite.domain.Favorite;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

public record FavoriteResponse(
        @Schema(description = "즐겨찾기 ID", example = "1")
        Long favoriteId,
        @Schema(description = "TMDB 영화 ID", example = "550")
        Long movieId,
        @Schema(description = "즐겨찾기 등록 일시", example = "2026-09-13T21:00:00")
        LocalDateTime createdAt
) {
    public static FavoriteResponse from(Favorite favorite) {
        return new FavoriteResponse(
                favorite.getFavoriteId(),
                favorite.getMovieId(),
                favorite.getCreatedAt()
        );
    }
}