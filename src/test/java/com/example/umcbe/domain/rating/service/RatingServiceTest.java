package com.example.umcbe.domain.rating.service;


import com.example.umcbe.domain.member.domain.Member;
import com.example.umcbe.domain.member.repository.MemberRepository;
import com.example.umcbe.domain.rating.domain.Rating;
import com.example.umcbe.domain.rating.dto.CreateRatingRequest;
import com.example.umcbe.domain.rating.dto.UpdateRatingRequest;
import com.example.umcbe.domain.rating.repository.RatingRepository;
import com.example.umcbe.global.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private MemberRepository memberRepository;


    @InjectMocks
    private RatingService ratingService;

    private final Member member = Member.builder()
            .email("challenger@umc.com")
            .password("암호화된비밀번호")
            .nickname("영화조아")
            .build();



    private final Rating rating = Rating.builder()
            .score(5)
            .member(member)
            .movieId(1L)
            .comment("good")
            .build();



    @Test
    @DisplayName("평점을 등록하지 않은 영화에 평점을 등록한다")
    void createRating() {
        given(memberRepository.findById(1L)).willReturn(Optional.of(member));
        given(ratingRepository.existsByMember_MemberIdAndMovieId(1L,1L)).willReturn(false);
        given(ratingRepository.save(any(Rating.class))).willAnswer(invocation -> {
            Rating rating = invocation.getArgument(0);
            ReflectionTestUtils.setField(rating, "ratingId", 1L);
            return rating;
        });

        Long response = ratingService.create(1L,new CreateRatingRequest(1L,5,"good"));

        assertThat(response).isEqualTo(1L);
        verify(ratingRepository).save(any(Rating.class));
    }


    @Test
    @DisplayName("같은 영화에 평점을 중복 등록한다")
    void createRatingWhenDuplicated() {
        given(memberRepository.findById(1L)).willReturn(Optional.of(member));
        given(ratingRepository.existsByMember_MemberIdAndMovieId(1L,1L)).willReturn(true);


        assertThatThrownBy(()-> ratingService.create(1L,new CreateRatingRequest(1L,2,"good")))
                .isInstanceOf(CustomException.class)
                .hasMessage("이미 이 영화에 평점을 남기셨습니다.");

        verify(ratingRepository,never()).save(any(Rating.class));
    }


    @Test
    @DisplayName("다른 사용자의 평점을 수정한다.")
    void updateRating() {
        ReflectionTestUtils.setField(member,"memberId",1L);
        ReflectionTestUtils.setField(rating,"ratingId",1L);

        given(ratingRepository.findById(1L)).willReturn(Optional.of(rating));

        assertThatThrownBy(()-> ratingService.update(1L,2L,new UpdateRatingRequest(5,"good")))
                .isInstanceOf(CustomException.class)
                .hasMessage("본인이 작성한 평점만 수정하거나 삭제할 수 있습니다.");
    }
}
