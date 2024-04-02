package com.beside.interviewserver.domain.redis;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@RedisHash(value="refreshToken", timeToLive = 604800000)
public class RefreshToken {

    @Id
    private Long userId;
    private String refreshToken;

    public static RefreshToken createRefreshToken(Long userId,String refreshToken){
        return RefreshToken.builder()
                .userId(userId)
                .refreshToken(refreshToken)
                .build();
    }
}
