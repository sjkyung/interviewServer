package com.bisise.interviewserver.api.sign.dto.response.naver;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NaverTokenResponse (
        String scope,
        @JsonProperty("id_token")
        String idToken,
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("refresh_token")
        String refreshToken,
        @JsonProperty("token_type")
        String tokenType,
        @JsonProperty("expires_in")
        String expiresIn,
        @JsonProperty("refresh_token_expires_in")
        String refreshTokenExpiresIn
) {
}
