package com.example.umcbe.domain.movierecord.controller;

import com.example.umcbe.domain.movierecord.dto.CreateMovieRecordRequest;
import com.example.umcbe.domain.movierecord.dto.MovieRecordResponse;
import com.example.umcbe.domain.movierecord.service.MovieRecordService;
import com.example.umcbe.global.code.SuccessCode;
import com.example.umcbe.global.interceptor.AuthInterceptor;
import com.example.umcbe.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MovieRecordController {

    private final MovieRecordService movieRecordService;

    @PostMapping("/movie-records")
    public ResponseEntity<ApiResponse<MovieRecordResponse>> create(
            @RequestAttribute(AuthInterceptor.MEMBER_ID) Long memberId,
            @RequestBody @Valid CreateMovieRecordRequest request
    ) {
        MovieRecordResponse response = movieRecordService.create(memberId, request);
        return ApiResponse.success(SuccessCode.COMMON_CREATED,response);
    }
}