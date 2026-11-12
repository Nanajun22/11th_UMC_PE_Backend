package com.example.umcbe.domain.movierecord.dto;


import com.example.umcbe.domain.favorite.dto.FavoriteResponse;
import com.example.umcbe.domain.rating.dto.RatingResponse;

public record MovieRecordResponse(
        RatingResponse rating,
        FavoriteResponse favorite
) {
}