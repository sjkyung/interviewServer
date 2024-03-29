package com.bisise.interviewserver.api.sign.service.impl;

import com.bisise.interviewserver.api.sign.dto.request.SignInRequest;
import com.bisise.interviewserver.api.sign.dto.response.SignInResponse;
import com.bisise.interviewserver.api.sign.dto.response.google.GoogleTokenResponse;
import com.bisise.interviewserver.api.sign.dto.response.google.GoogleUserInfoResponse;
import com.bisise.interviewserver.api.sign.dto.response.naver.NaverTokenResponse;
import com.bisise.interviewserver.api.sign.dto.response.naver.NaverUserInfoResponse;
import com.bisise.interviewserver.api.sign.service.SignService;
import com.bisise.interviewserver.common.auth.jwt.JwtProvider;
import com.bisise.interviewserver.common.auth.jwt.JwtValidator;
import com.bisise.interviewserver.common.auth.jwt.Token;
import com.bisise.interviewserver.common.config.ProviderConfig;
import com.bisise.interviewserver.domain.user.User;
import com.bisise.interviewserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static com.bisise.interviewserver.domain.user.User.createSocialUser;
import static com.bisise.interviewserver.domain.user.User.createUser;
@Service
@RequiredArgsConstructor
public class SignInGoogleService implements SignService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final JwtValidator jwtValidator;
    private final RestTemplate restTemplate;
    private final ProviderConfig providerConfig;

    private final String USER_KEY_PREFIX_GOOGLE = "GOOGLE_";
    @Override
    public SignInResponse signIn(SignInRequest request) {
        // Get accessToken - code를 받아 accessToken 취득
        GoogleTokenResponse googleTokenResponse = requestAccessToken(request.code());
        GoogleUserInfoResponse googleUserInfoResponse = requestUserInfo(googleTokenResponse.accessToken());
        // Already registered user
        Optional<User> user = getUser(Long.valueOf(googleUserInfoResponse.id()));
        if (user.isPresent()) {
            User userInfo = user.get();
            Token token = jwtProvider.issueToken(userInfo.getEmail());
            updateRefreshToken(token,userInfo);
            return SignInResponse.of(token,userInfo.getPlatformId());
        }
        // Sign up
        String providerKey = USER_KEY_PREFIX_GOOGLE + googleUserInfoResponse.id();

        Token token = jwtProvider.issueToken("");
        saveUser(googleUserInfoResponse.email(), providerKey) ;
        return SignInResponse.of(token, providerKey);
    }

    // 엑세스토큰
    private GoogleTokenResponse requestAccessToken(String code) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        System.out.println(code);
        System.out.println(URLDecoder.decode(code, StandardCharsets.UTF_8));

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();;
        body.add("grant_type", providerConfig.getGrantType());
        body.add("client_id", providerConfig.getGoogle().getClientId());
        body.add("client_secret", providerConfig.getGoogle().getClientSecret());
        body.add("redirect_uri", providerConfig.getGoogle().getRedirectUri());
        body.add("code", URLDecoder.decode(code, StandardCharsets.UTF_8));

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, httpHeaders);
        GoogleTokenResponse response = restTemplate.postForObject(
                providerConfig.getGoogle().getAccessTokenUri(),
                request,
                GoogleTokenResponse.class
        );
        // TODO! 토큰 정보를 가져오지 못하면 예외발생 처리 추가
        assert response != null;
        return response;
    }

    public GoogleUserInfoResponse requestUserInfo(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);
        HttpEntity request = new HttpEntity<>(httpHeaders);
        GoogleUserInfoResponse response = restTemplate.getForObject(providerConfig.getGoogle().getUserInfoUri() + "accessToken",GoogleUserInfoResponse.class,request);
        // TODO! 유저 정보를 가져오지 못하면 예외발생 처리 추가
        assert response != null;
        return response;
    }

    private Optional<User> getUser(Long id){
        return userRepository.findById(id);
    }
    private User saveUser(String email, String providerKey){
        User user = createSocialUser(email, providerKey);
        return userRepository.save(user);
    }
    private void updateRefreshToken(Token token, User user){
        user.updateRefreshToken(token.refreshToken());
    }
}
