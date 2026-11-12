package com.example.umcbe.domain.favorite.service;


import com.example.umcbe.domain.favorite.domain.Favorite;
import com.example.umcbe.domain.favorite.dto.CreateFavoriteRequest;
import com.example.umcbe.domain.favorite.dto.FavoriteResponse;
import com.example.umcbe.domain.favorite.dto.FavoriteStatusResponse;
import com.example.umcbe.domain.favorite.error.FavoriteErrorCode;
import com.example.umcbe.domain.favorite.repository.FavoriteRepository;
import com.example.umcbe.domain.member.domain.Member;
import com.example.umcbe.domain.member.error.MemberErrorCode;
import com.example.umcbe.domain.member.repository.MemberRepository;
import com.example.umcbe.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public FavoriteResponse create(Long memberId, CreateFavoriteRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (favoriteRepository.existsByMember_MemberIdAndMovieId(memberId, request.movieId())) {
            throw new CustomException(FavoriteErrorCode.FAVORITE_ALREADY_EXIST);
        }

        Favorite favorite = Favorite.builder()
                .member(member)
                .movieId(request.movieId())
                .build();

        return FavoriteResponse.from(favoriteRepository.save(favorite));
    }

    @Transactional(readOnly = true)
    public List<FavoriteResponse> getMyFavorites(Long memberId) {
        return favoriteRepository.findByMember_MemberIdOrderByFavoriteIdDesc(memberId).stream()
                .map(FavoriteResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public FavoriteStatusResponse getStatus(Long memberId, Long movieId) {
        boolean isFavorite = favoriteRepository.existsByMember_MemberIdAndMovieId(memberId, movieId);
        return new FavoriteStatusResponse(isFavorite);
    }

    @Transactional
    public void delete(Long memberId, Long movieId) {
        Favorite favorite = favoriteRepository.findByMember_MemberIdAndMovieId(memberId, movieId)
                .orElseThrow(() -> new CustomException(FavoriteErrorCode.FAVORITE_NOT_FOUND));

        favoriteRepository.delete(favorite);
    }
}