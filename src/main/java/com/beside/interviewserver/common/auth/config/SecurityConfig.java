package com.beside.interviewserver.common.auth.config;

import com.beside.interviewserver.common.auth.ExceptionHandlerFilter;
import com.beside.interviewserver.common.auth.JwtAuthenticationEntrypoint;
import com.beside.interviewserver.common.auth.JwtAuthenticationFilter;
import com.beside.interviewserver.common.auth.jwt.JwtProvider;
import com.beside.interviewserver.common.auth.jwt.JwtValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationEntrypoint jwtAuthenticationEntrypoint;
    private final JwtValidator jwtValidator;
    private final JwtProvider jwtProvider;

    private final String[] whiteList = {
            "/",
            "/api/users/signIn",
            "/api/users/signUp",
            "/api/users/reissue",
            "/api/resume-question",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/api-docs/**",
            "/files/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                            httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                            httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(jwtAuthenticationEntrypoint)
                )
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                            authorizationManagerRequestMatcherRegistry.anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtValidator,jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new ExceptionHandlerFilter(), JwtAuthenticationFilter.class)
                .build();
    }

    @Bean
    public WebSecurityCustomizer customizer(){
        return web -> web.ignoring().requestMatchers(whiteList);
    }

}
