package com.example.umcbe.domain.movierecord.service;


import com.example.umcbe.domain.favorite.domain.Favorite;
import com.example.umcbe.domain.favorite.dto.FavoriteResponse;
import com.example.umcbe.domain.favorite.error.FavoriteErrorCode;
import com.example.umcbe.domain.favorite.repository.FavoriteRepository;
import com.example.umcbe.domain.member.domain.Member;
import com.example.umcbe.domain.member.error.MemberErrorCode;
import com.example.umcbe.domain.member.repository.MemberRepository;
import com.example.umcbe.domain.movierecord.dto.CreateMovieRecordRequest;
import com.example.umcbe.domain.movierecord.dto.MovieRecordResponse;
import com.example.umcbe.domain.rating.domain.Rating;
import com.example.umcbe.domain.rating.dto.RatingResponse;
import com.example.umcbe.domain.rating.errror.RatingErrorCode;
import com.example.umcbe.domain.rating.repository.RatingRepository;
import com.example.umcbe.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MovieRecordService {

    private final MemberRepository memberRepository;
    private final RatingRepository ratingRepository;
    private final FavoriteRepository favoriteRepository;

    @Transactional
    public MovieRecordResponse create(Long memberId, CreateMovieRecordRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (ratingRepository.existsByMember_MemberIdAndMovieId(memberId, request.movieId())) {
            throw new CustomException(RatingErrorCode.ALREADY_RATED);
        }

        if (request.addToFavorites()
                && favoriteRepository.existsByMember_MemberIdAndMovieId(memberId, request.movieId())) {
            throw new CustomException(FavoriteErrorCode.FAVORITE_ALREADY_EXIST);
        }

        Rating rating = Rating.builder()
                .member(member)
                .movieId(request.movieId())
                .score(request.score())
                .comment(request.comment())
                .build();
        Rating savedRating = ratingRepository.save(rating);

        FavoriteResponse favoriteResponse = null;
        if (request.addToFavorites()) {
            Favorite favorite = Favorite.builder()
                    .member(member)
                    .movieId(request.movieId())
                    .build();
            Favorite savedFavorite = favoriteRepository.save(favorite);
            favoriteResponse = FavoriteResponse.from(savedFavorite);
        }

        return new MovieRecordResponse(
                RatingResponse.from(savedRating),
                favoriteResponse
        );
    }
}