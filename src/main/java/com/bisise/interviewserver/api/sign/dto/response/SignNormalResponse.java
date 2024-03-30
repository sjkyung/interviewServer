package com.bisise.interviewserver.api.sign.dto.response;

import com.bisise.interviewserver.common.auth.jwt.Token;
import com.bisise.interviewserver.common.types.PlatformType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record SignNormalResponse(
        String accessToken,
        String refreshToken,
        Long userId,
        String nick,
        String careerExperience,
        String jobPosition,
        String platformId,
        @Enumerated(EnumType.STRING)
        PlatformType platform

) {
    public static SignNormalResponse of(Token token,Long userId, String nick, String careerExperience, String jobPosition, String platformId, PlatformType platformType ){
        return SignNormalResponse.builder()
                .accessToken(token.accessToken())
                .refreshToken(token.refreshToken())
                .userId(userId)
                .nick(nick)
                .careerExperience(careerExperience)
                .jobPosition(jobPosition)
                .platformId(platformId)
                .platform(platformType)
                .build();
    }

}
