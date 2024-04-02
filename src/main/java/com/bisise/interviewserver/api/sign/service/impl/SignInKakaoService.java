package com.bisise.interviewserver.api.sign.service.impl;

import com.bisise.interviewserver.api.sign.dto.request.SignInRequest;
import com.bisise.interviewserver.api.sign.dto.response.SignInResponse;
import com.bisise.interviewserver.api.sign.dto.response.kakao.KakaoTokenResponse;
import com.bisise.interviewserver.api.sign.dto.response.kakao.KakaoUserInfoResponse;
import com.bisise.interviewserver.api.sign.service.SignInCommonService;
import com.bisise.interviewserver.api.sign.service.SignService;
import com.bisise.interviewserver.common.auth.jwt.JwtProvider;
import com.bisise.interviewserver.common.auth.jwt.JwtValidator;
import com.bisise.interviewserver.common.auth.jwt.Token;
import com.bisise.interviewserver.common.config.ProviderConfig;
import com.bisise.interviewserver.domain.user.User;
import com.bisise.interviewserver.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.bisise.interviewserver.domain.user.User.createSocialUser;
import static com.bisise.interviewserver.domain.user.User.createUser;

@Service
@RequiredArgsConstructor
public class SignInKakaoService implements SignService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final JwtValidator jwtValidator;
    private final RestTemplate restTemplate;
    private final ProviderConfig providerConfig;
    private final String USER_KEY_PREFIX_KAKAO = "KAKAO_";

    @Override
    @Transactional
    public SignInResponse signIn(SignInRequest request) {
        // Get accessToken - code를 받아 accessToken 취득
        KakaoTokenResponse kakaoTokenResponse = requestAccessToken(request.code());
        // Get userInfo using an accessToken - accessToken 값으로 유저 정보 겟
        KakaoUserInfoResponse kakaoUserInfoResponse = requestUserInfo(kakaoTokenResponse.accessToken());

        // Already registered user
        Optional<User> user = getUser(kakaoUserInfoResponse.id());
        if (user.isPresent()) {
            User userInfo = user.get();
            Token token = jwtProvider.issueToken(userInfo.getUserId());
            updateRefreshToken(token,userInfo);
            return SignInResponse.of(token,userInfo.getPlatformId());
        }
        // Sign up
        String providerKey = USER_KEY_PREFIX_KAKAO + kakaoUserInfoResponse.id();
        Token token = jwtProvider.issueToken(/*userId이 없는 경우는 ? */0L);
        saveUser(null, USER_KEY_PREFIX_KAKAO + kakaoUserInfoResponse.id()) ;
        return SignInResponse.of(token, providerKey);
    }

    private KakaoTokenResponse requestAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();;
        body.add("grant_type", providerConfig.getGrantType());
        body.add("client_id", providerConfig.getKakao().getClientId());
        body.add("redirect_uri", providerConfig.getKakao().getRedirectUri());
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, httpHeaders);
        KakaoTokenResponse response = restTemplate.postForObject(
                providerConfig.getKakao().getAccessTokenUri(),
                request,
                KakaoTokenResponse.class
        );

        // TODO! 토큰 정보를 가져오지 못하면 예외발생 처리 추가
        assert response != null;
        return response;
    }

    public KakaoUserInfoResponse requestUserInfo(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, httpHeaders);
        KakaoUserInfoResponse response = restTemplate.postForObject(
                providerConfig.getKakao().getUserInfoUri(),
                request,
                KakaoUserInfoResponse.class
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
