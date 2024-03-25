package com.bisise.interviewserver.common.exception;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;

import java.util.Collection;

public class UserAuthentication extends UsernamePasswordAuthenticationToken {

    private UserAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities){
        super(principal, credentials, authorities);
    }

    public static UserAuthentication createUserAuthentication(Long userId){
        return new UserAuthentication(userId, null, null);
    }
}
