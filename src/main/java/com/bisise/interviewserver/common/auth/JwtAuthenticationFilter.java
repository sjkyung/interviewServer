package com.bisise.interviewserver.common.auth;

import com.bisise.interviewserver.common.auth.jwt.JwtProvider;
import com.bisise.interviewserver.common.auth.jwt.JwtValidator;
import com.bisise.interviewserver.common.exception.UnauthorizedException;
import com.bisise.interviewserver.common.message.ErrorMessage;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.bisise.interviewserver.common.auth.UserAuthentication.createUserAuthentication;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtValidator jwtValidator;
    private final JwtProvider jwtProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = getAccessToken(request);
        jwtValidator.validateAccessToken(accessToken);
        doAuthentication(request, jwtProvider.getSubject(accessToken));
        filterChain.doFilter(request,response);
    }

    private String getAccessToken(HttpServletRequest request){
        String accessToken = request.getHeader("Authorization");
        if(StringUtils.hasText(accessToken) && accessToken.startsWith("Bearer ")){
            return accessToken.substring("Bearer ".length());
        }
        throw new UnauthorizedException(ErrorMessage.INVALID_ACCESS_TOKEN);
    }

    private void doAuthentication(HttpServletRequest request, Long id){
       UserAuthentication userAuthentication = createUserAuthentication(id);
       createWebAndAuthentication(request, userAuthentication);
       SecurityContext context = SecurityContextHolder.getContext();
       context.setAuthentication(userAuthentication);
    }


    private void createWebAndAuthentication(HttpServletRequest request, UserAuthentication userAuthentication){
        WebAuthenticationDetailsSource webAuthenticationDetailsSource = new WebAuthenticationDetailsSource();
        WebAuthenticationDetails webAuthenticationDetails = webAuthenticationDetailsSource.buildDetails(request);
        userAuthentication.setDetails(webAuthenticationDetails);
    }
}
