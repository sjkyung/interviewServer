package com.beside.interviewserver.api.user.dto.response;

import com.beside.interviewserver.common.auth.jwt.Token;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record UserSignInResponse(String accessToken,
                                 String refreshToken,
                                 Long userId) {

    public static UserSignInResponse of(Token token, Long userId){
        return UserSignInResponse.builder()
                .accessToken(token.accessToken())
                .refreshToken(token.refreshToken())
                .userId(userId)
                .build();
    }
}
