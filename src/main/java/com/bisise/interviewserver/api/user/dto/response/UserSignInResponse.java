package com.bisise.interviewserver.api.user.dto.response;

import com.bisise.interviewserver.common.auth.jwt.Token;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record UserSignInResponse(String accessToken,
                                 String refreshToken,
                                 String email) {

    public static UserSignInResponse of(Token token, String email){
        return UserSignInResponse.builder()
                .accessToken(token.accessToken())
                .refreshToken(token.refreshToken())
                .email(email)
                .build();
    }
}
