package com.bisise.interviewserver.common.auth.jwt;

import com.bisise.interviewserver.common.exception.UnauthorizedException;
import com.bisise.interviewserver.common.message.ErrorMessage;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class JwtValidator {
    private final JwtGenerator jwtGenerator;


    public void validateAccessToken(String accessToken){
        try{
            parseToken(accessToken);
        }catch (ExpiredJwtException e){
            throw new UnauthorizedException(ErrorMessage.EXPIRED_ACCESS_TOKEN);
        }catch (Exception e){
            throw new UnauthorizedException(ErrorMessage.INVALID_ACCESS_TOKEN_VALUE);
        }
    }

    public void validateRefreshToken(String refreshToken){
        try {
            parseToken(refreshToken);
        }catch (ExpiredJwtException e){
            throw new UnauthorizedException(ErrorMessage.EXPIRED_REFRESH_TOKEN);
        }catch (Exception e){
            throw new UnauthorizedException(ErrorMessage.INVALID_REFRESH_TOKEN_VALUE);
        }
    }

    private void parseToken(String token){
        JwtParser jwtParser = jwtGenerator.getJwtParser();
        jwtParser.parseClaimsJws(token);
    }

}
