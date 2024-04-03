package com.beside.interviewserver.api.user.service;


import com.beside.interviewserver.api.user.dto.request.UserReissueRequest;
import com.beside.interviewserver.api.user.dto.request.UserSignInRequest;
import com.beside.interviewserver.api.user.dto.request.UserSignUpRequest;
import com.beside.interviewserver.api.user.dto.response.UserSignInResponse;
import com.beside.interviewserver.api.user.dto.response.UserSignUpResponse;
import com.beside.interviewserver.common.auth.jwt.JwtProvider;
import com.beside.interviewserver.common.auth.jwt.JwtValidator;
import com.beside.interviewserver.common.auth.jwt.Token;
import com.beside.interviewserver.common.exception.ConflictException;
import com.beside.interviewserver.common.exception.EntityNotFoundException;
import com.beside.interviewserver.common.exception.UnauthorizedException;
import com.beside.interviewserver.common.message.ErrorMessage;
import com.beside.interviewserver.domain.redis.RefreshToken;
import com.beside.interviewserver.domain.redis.repository.RefreshTokenRepository;
import com.beside.interviewserver.domain.user.User;
import com.beside.interviewserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.beside.interviewserver.domain.user.User.createUser;
import static com.beside.interviewserver.domain.redis.RefreshToken.createRefreshToken;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final JwtValidator jwtValidator;

    @Transactional(readOnly = true)
    public void splash(Long userId){
        getUser(userId);
    }

    public void findNickname(String nickname) {
        userRepository.findByNick(nickname)
                .ifPresent((s) -> {
                    throw new EntityNotFoundException(ErrorMessage.ALREADY_IN_USE);
                });
    }

    @Transactional
    public UserSignUpResponse signUp(UserSignUpRequest userSignUpRequest){
        validateDuplicateUser(userSignUpRequest.email());
        User user = saveUser(userSignUpRequest);
        Token token = jwtProvider.issueToken(user.getUserId());
        updateRefreshToken(token, user);
        return UserSignUpResponse.of(token,user.getUserId());
    }

    @Transactional
    public UserSignInResponse signIn(UserSignInRequest userSignInRequest){
        User user = getUser(userSignInRequest.email());
        Token token = jwtProvider.issueToken(user.getUserId());
        updateRefreshToken(token,user);
        return UserSignInResponse.of(token,user.getUserId());
    }

    public void signOut(Long userId) {
        User findUser = getUser(userId);
        deleteRefreshToken(findUser);
    }

    @Transactional(noRollbackFor = UnauthorizedException.class)
    public UserSignUpResponse reissue(String refreshToken, UserReissueRequest request) {
        Long userId = request.userId();
        validateRefreshToken(refreshToken, userId);
        User findUser = getUser(userId);
        Token issueToken = jwtProvider.issueToken(userId);
        updateRefreshToken(issueToken, findUser);
        return UserSignUpResponse.of(issueToken, findUser.getUserId());
    }



    private User getUser(String Email){
        return userRepository.findByEmail(Email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND));
    }

    private User getUser(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND));
    }




    private User saveUser(UserSignUpRequest userSignUpRequest){
        //User user = createUser(userSignUpRequest.email());
        //return userRepository.save(user);
        return null;
    }

    private void updateRefreshToken(Token token, User user){
        user.updateRefreshToken(token.refreshToken());
    }

    private void deleteRefreshToken(User user){
        user.updateRefreshToken(null);
    }

    private void validateRefreshToken(String refreshToken, Long userId) {
        try {
            jwtValidator.validateRefreshToken(refreshToken);
        } catch (UnauthorizedException e) {
            signOut(userId);
            throw e;
        }
    }

    private void validateDuplicateUser(String email){
        if(userRepository.existsUserByEmail(email)){
            throw new ConflictException(ErrorMessage.DUPLICATE_USER);
        }
    }

}
