package com.bisise.interviewserver.common.auth.jwt;

import io.jsonwebtoken.JwtParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final JwtGenerator jwtGenerator;


    public Token issueToken(String email){
        return Token.of(jwtGenerator.generateToken(email,true),
                jwtGenerator.generateToken(email, false));

    }

    public String getSubject(String token){
        JwtParser jwtParser = jwtGenerator.getJwtParser();
        return jwtParser.parseClaimsJws(token).getBody().getSubject();
    }
}
