package com.example.umcbe.domain.rating.controller;


import com.example.umcbe.domain.rating.dto.CreateRatingRequest;
import com.example.umcbe.domain.rating.dto.RatingResponse;
import com.example.umcbe.domain.rating.dto.UpdateRatingRequest;
import com.example.umcbe.domain.rating.service.RatingService;
import com.example.umcbe.global.code.SuccessCode;
import com.example.umcbe.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class RatingController {
    private final RatingService ratingService;

    @GetMapping("/{memberId}/ratings")
    public List<RatingResponse> getMemberRatings(@PathVariable Long memberId) {
        return ratingService.getRatingsByMember(memberId);
    }

    @PostMapping("/{memberId}/ratings")
    public ResponseEntity<ApiResponse<Long>> create(
            @PathVariable Long memberId,
            @Valid @RequestBody CreateRatingRequest request
    ) {
        Long ratingId = ratingService.create(memberId, request);
        return ApiResponse.success(SuccessCode.COMMON_CREATED,ratingId);
    }

    @PatchMapping("/{memberId}/ratings/{ratingId}")
    public ResponseEntity<ApiResponse<Void>> update(
            @PathVariable Long memberId,
            @PathVariable Long ratingId,
            @Valid @RequestBody UpdateRatingRequest request
    ) {
        ratingService.update(ratingId, memberId, request);
        return ApiResponse.success(SuccessCode.COMMON_OK);
    }

    @DeleteMapping("/{memberId}/ratings/{ratingId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long memberId, @PathVariable Long ratingId) {
        ratingService.delete(ratingId, memberId);
        return ApiResponse.success(SuccessCode.COMMON_DELETED);
    }
}