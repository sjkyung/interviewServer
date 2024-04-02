package com.bisise.interviewserver.common.auth.jwt;


import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtParser;

@Component
public class JwtGenerator {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.access-token-expire-time}")
    private long accessTokenExpireTime;
    @Value("${jwt.refresh-token-expire-time}")
    private long refreshTokenExpireTime;


    public String generateToken(Long userId, boolean isAccessToken){
        final Date now = generateDate();
        final Date expiration = generateExpiarationDate(now, isAccessToken);
        return Jwts.builder()
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
                .setSubject(String.valueOf(userId))
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public JwtParser getJwtParser(){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build();
    }

    private Date generateDate(){
        return new Date();
    }

    private Date generateExpiarationDate(Date now, boolean isAccessToken){
        return new Date(now.getTime() + booleanTokenTime(isAccessToken));
    }

    private long booleanTokenTime(boolean isAccessToken){
        if(isAccessToken){
            return accessTokenExpireTime;
        }
        return refreshTokenExpireTime;
    }

    private Key getSignKey(){
        return Keys.hmacShaKeyFor(
                Base64.getEncoder().encodeToString(secretKey.getBytes()).getBytes()
        );
    }
}
