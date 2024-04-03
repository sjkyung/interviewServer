package com.beside.interviewserver.api.user.dto.response;

import com.beside.interviewserver.common.auth.jwt.Token;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record UserSignUpResponse(
        String accessToken,
        String refreshToken,
        Long userId
) {
    public static UserSignUpResponse of(Token token, Long userId){
        return UserSignUpResponse.builder()
                .accessToken(token.accessToken())
                .refreshToken(token.refreshToken())
                .userId(userId)
                .build();
    }

}
