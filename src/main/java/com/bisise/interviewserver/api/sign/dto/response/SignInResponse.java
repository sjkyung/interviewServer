package com.bisise.interviewserver.api.sign.dto.response;

import com.bisise.interviewserver.common.auth.jwt.Token;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record SignInResponse(String accessToken,
                             String refreshToken,
                             String email) {

    public static SignInResponse of(Token token, String email){
        return SignInResponse.builder()
                .accessToken(token.accessToken())
                .refreshToken(token.refreshToken())
                .email(email)
                .build();
    }
}
