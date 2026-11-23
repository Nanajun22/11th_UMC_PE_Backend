package com.example.umcbe.domain.rating.controller;

import com.example.umcbe.domain.member.domain.Member;
import com.example.umcbe.domain.member.repository.MemberRepository;
import com.example.umcbe.domain.rating.dto.CreateRatingRequest;
import com.example.umcbe.domain.rating.repository.RatingRepository;
import com.example.umcbe.global.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private Member member;

    private String authorizationHeader;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(Member.builder()
                .email("ss@gmail.com")
                .password("1234")
                .nickname("kim").build());

        authorizationHeader = "Bearer " + jwtUtil.createAccessToken(member.getMemberId(), member.getEmail());
    }

    @Test
    @DisplayName("토큰을 담아 평점을 등록하면 201과 COMMON_201을 응답한다.")
    void createRating() throws Exception {
        mockMvc.perform(post("/ratings")
                    .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(new CreateRatingRequest(1L,5,"good"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("COMMON_201"))
                .andExpect(jsonPath("$.data").isNumber());


    }


    @Test
    @DisplayName("토큰없이 평점을 등록하면 401과 AUTH_401_2를 응답한다.")
    void createRatingWithoutToken() throws Exception {
        mockMvc.perform(post("/ratings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CreateRatingRequest(1L,5,"good"))))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("AUTH_401_2"));


    }


}
