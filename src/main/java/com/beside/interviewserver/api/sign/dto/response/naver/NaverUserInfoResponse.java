package com.beside.interviewserver.api.sign.dto.response.naver;

import com.beside.interviewserver.api.sign.dto.response.kakao.KakaoUserInfoResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.Date;

@Builder(access = AccessLevel.PRIVATE)
public record NaverUserInfoResponse (
        String resultcode,

        String message,
        @JsonProperty("response")
        NaverAccount naverAccount
) {
    public static record NaverAccount (
            String id,
            String nickname,
            String email,
            String name
    ) {}
}
