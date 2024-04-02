package com.beside.interviewserver.api.sign.service.impl;

import com.beside.interviewserver.api.sign.dto.request.SignInNormalRequest;
import com.beside.interviewserver.api.sign.dto.request.SignUpNormalRequest;
import com.beside.interviewserver.api.sign.dto.response.SignNormalResponse;
import com.beside.interviewserver.common.auth.jwt.JwtProvider;
import com.beside.interviewserver.common.auth.jwt.JwtValidator;
import com.beside.interviewserver.common.auth.jwt.Token;
import com.beside.interviewserver.common.config.ProviderConfig;
import com.beside.interviewserver.common.exception.EntityNotFoundException;
import com.beside.interviewserver.common.message.ErrorMessage;
import com.beside.interviewserver.domain.redis.repository.RefreshTokenRepository;
import com.beside.interviewserver.domain.user.User;
import com.beside.interviewserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.beside.interviewserver.domain.redis.RefreshToken.createRefreshToken;
import static com.beside.interviewserver.domain.user.User.createUser;

@Service
@RequiredArgsConstructor
public class SignInNormalService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final JwtValidator jwtValidator;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ProviderConfig providerConfig;


    public SignNormalResponse signIn(SignInNormalRequest request) {
        User user = userRepository.findByPlatformId(request.platform() + "_" + request.platformId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND));
        Token token = jwtProvider.issueToken(user.getUserId());
        user.updateRefreshToken(token.refreshToken());
        return SignNormalResponse.of(token,user.getUserId(), user.getNick(),user.getCareerExperience(),user.getJobPosition(),user.getPlatformId(),user.getPlatform());
    }

    public SignNormalResponse signUp(SignUpNormalRequest request) {
        User user = saveUser(request);
        Token token = jwtProvider.issueToken(user.getUserId());
        user.updateRefreshToken(token.refreshToken());
        return SignNormalResponse.of(token,user.getUserId(),user.getNick(),user.getCareerExperience(),user.getJobPosition(),user.getPlatformId(),user.getPlatform());
    }

    private Optional<User> getUser(Long id){
        return userRepository.findById(id);
    }
    private User saveUser(SignUpNormalRequest signUpNormalRequest){
        User user = createUser(signUpNormalRequest.nick(),
                signUpNormalRequest.careerExperience(),
                signUpNormalRequest.jobPosition(),
                signUpNormalRequest.platform()+"_"+signUpNormalRequest.platformId(),
                signUpNormalRequest.platform());
        return userRepository.save(user);
    }
    private void updateRefreshToken(Token token, User user){
        user.updateRefreshToken(token.refreshToken());
        refreshTokenRepository.save(createRefreshToken(user.getUserId(),token.refreshToken()));
    }

    private void deleteRefreshToken(User user){
        user.updateRefreshToken(null);
        refreshTokenRepository.deleteById(user.getUserId());
    }

}
