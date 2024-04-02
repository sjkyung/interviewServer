package com.beside.interviewserver.api.sign.dto.response.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

public record KakaoTokenResponse (

        @JsonProperty("id_token")
        String idToken,
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("token_type")
        String tokenType,
        @JsonProperty("refresh_token")
        String refreshToken,
        String scope,
        @JsonProperty("expires_in")
        String expiresIn,
        @JsonProperty("refresh_token_expires_in")
        String refreshTokenExpiresIn
) {
}
