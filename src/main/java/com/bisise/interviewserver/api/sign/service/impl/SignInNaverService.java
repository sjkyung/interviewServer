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
public class SignInNaverService implements SignService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final JwtValidator jwtValidator;
    private final RestTemplate restTemplate;
    private final ProviderConfig providerConfig;

    private final String USER_KEY_PREFIX_NAVER = "NAVER_";
    @Override
    public SignInResponse signIn(SignInRequest request) {
        NaverTokenResponse naverTokenResponse = requestAccessToken(request.code());
        NaverUserInfoResponse naverUserInfoResponse = requestUserInfo(naverTokenResponse.accessToken());
        System.out.println(naverUserInfoResponse.naverAccount().id());
        // Already registered user
        Optional<User> user = getUser(1L);
        if (user.isPresent()) {
            User userInfo = user.get();
            Token token = jwtProvider.issueToken(userInfo.getUserId());
            updateRefreshToken(token,userInfo);
            return SignInResponse.of(token,userInfo.getPlatformId());
        }

        String providerKey = USER_KEY_PREFIX_NAVER + naverUserInfoResponse.naverAccount().id();
        Token token = jwtProvider.issueToken(/*userId이 없는 경우는 ? */ 0L);
        saveUser(naverUserInfoResponse.naverAccount().email(), providerKey) ;
        return SignInResponse.of(token, providerKey);
    }

    private NaverTokenResponse requestAccessToken(String code) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();;
        body.add("grant_type", providerConfig.getGrantType());
        body.add("client_id", providerConfig.getNaver().getClientId());
        body.add("client_secret", providerConfig.getNaver().getClientSecret());
        body.add("state", providerConfig.getNaver().getState());
        body.add("code", URLDecoder.decode(code, StandardCharsets.UTF_8));

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, httpHeaders);
        NaverTokenResponse response = restTemplate.postForObject(
                providerConfig.getNaver().getAccessTokenUri(),
                request,
                NaverTokenResponse.class
        );
        // TODO! 토큰 정보를 가져오지 못하면 예외발생 처리 추가
        assert response != null;
        return response;
    }

    private NaverUserInfoResponse requestUserInfo(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + URLDecoder.decode(accessToken, StandardCharsets.UTF_8));
        HttpEntity request = new HttpEntity<>(httpHeaders);
        NaverUserInfoResponse response = restTemplate.postForObject(
                providerConfig.getNaver().getUserInfoUri(),
                request,
                NaverUserInfoResponse.class
        );
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
