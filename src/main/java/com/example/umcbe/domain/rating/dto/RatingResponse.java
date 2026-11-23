package com.example.umcbe.domain.rating.dto;


import com.example.umcbe.domain.rating.domain.Rating;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

public record RatingResponse(
        @Schema(description = "평점 ID", example = "1")
        Long ratingId,
        @Schema(description = "TMDB 영화 ID", example = "550")
        Long movieId,
        @Schema(description = "별점 (1~5)", example = "5")
        Integer score,
        @Schema(description = "후기", example = "인생 영화입니다.", nullable = true)
        String comment,
        @Schema(description = "작성 일시", example = "2026-09-13T21:00:00")
        LocalDateTime createdAt,
        @Schema(description = "마지막 수정 일시", example = "2026-09-13T21:00:00")
        LocalDateTime updatedAt
) {
    public static RatingResponse from(Rating rating) {
        return new RatingResponse(
                rating.getRatingId(),
                rating.getMovieId(),
                rating.getScore(),
                rating.getComment(),
                rating.getCreatedAt(),
                rating.getUpdatedAt()
        );
    }
}