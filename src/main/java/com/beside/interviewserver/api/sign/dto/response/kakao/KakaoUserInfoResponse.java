package com.beside.interviewserver.api.sign.dto.response.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.Date;

@Builder(access = AccessLevel.PRIVATE)
public record KakaoUserInfoResponse(
        Long id,
        @JsonProperty("connected_at")
        Date connectedAt,
        KakaoProperties properties,
        @JsonProperty("kakao_account")
        KakaoAccount kakaoAccount
) {
    static record KakaoProperties (
            String nickname,
            @JsonProperty("profile_image")
            String profileImage
    ) {}
    static record KakaoAccount (
        @JsonProperty("profile_nickname_needs_agreement")
        String profileNicknameNeedsAgreement,
        KakaoProfile profile
    ) {}
    static record KakaoProfile(
         String nickname,
         @JsonProperty("thumbnail_image_url")
         String thumbnailImageUrl,
         @JsonProperty("profile_image_url")
         String profileImageUrl
    ) {}
}
