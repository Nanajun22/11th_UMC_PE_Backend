package com.example.umcbe.domain.rating.service;


import com.example.umcbe.domain.member.domain.Member;
import com.example.umcbe.domain.member.error.MemberErrorCode;
import com.example.umcbe.domain.member.repository.MemberRepository;
import com.example.umcbe.domain.rating.domain.Rating;
import com.example.umcbe.domain.rating.dto.CreateRatingRequest;
import com.example.umcbe.domain.rating.dto.RatingResponse;
import com.example.umcbe.domain.rating.dto.UpdateRatingRequest;
import com.example.umcbe.domain.rating.errror.RatingErrorCode;
import com.example.umcbe.domain.rating.repository.RatingRepository;
import com.example.umcbe.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<RatingResponse> getRatingsByMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new CustomException(MemberErrorCode.MEMBER_NOT_FOUND);
        }

        return ratingRepository.findByMember_MemberIdOrderByRatingIdDesc(memberId).stream()
                .map(RatingResponse::from)
                .toList();
    }

    @Transactional
    public Long create(Long memberId, CreateRatingRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MemberErrorCode.MEMBER_NOT_FOUND));


        if (ratingRepository.existsByMember_MemberIdAndMovieId(memberId, request.movieId())) {
            throw new CustomException(RatingErrorCode.ALREADY_RATED);
        }

        Rating rating = Rating.builder()
                .member(member)
                .movieId(request.movieId())
                .score(request.score())
                .comment(request.comment())
                .build();

        return ratingRepository.save(rating).getRatingId();
    }

    @Transactional
    public void update(Long ratingId, Long memberId, UpdateRatingRequest request) {
        Rating rating = findOwnedRating(ratingId, memberId);

        rating.update(request.score(), request.comment());
        ratingRepository.save(rating);
    }

    @Transactional
    public void delete(Long ratingId, Long memberId) {
        Rating rating = findOwnedRating(ratingId, memberId);

        ratingRepository.delete(rating);
    }


    private Rating findOwnedRating(Long ratingId, Long memberId) {
        Rating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new CustomException(RatingErrorCode.RATING_NOT_FOUND));

        if (!rating.getMember().getMemberId().equals(memberId)) {
            throw new CustomException(RatingErrorCode.NOT_MY_RATING);
        }

        return rating;
    }
}