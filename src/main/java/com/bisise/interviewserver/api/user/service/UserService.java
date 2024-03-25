package com.bisise.interviewserver.api.user.service;


import com.bisise.interviewserver.api.user.dto.request.UserReissueRequest;
import com.bisise.interviewserver.api.user.dto.request.UserSignInRequest;
import com.bisise.interviewserver.api.user.dto.request.UserSignUpRequest;
import com.bisise.interviewserver.api.user.dto.response.UserSignInResponse;
import com.bisise.interviewserver.api.user.dto.response.UserSignUpResponse;
import com.bisise.interviewserver.common.auth.jwt.JwtProvider;
import com.bisise.interviewserver.common.auth.jwt.JwtValidator;
import com.bisise.interviewserver.common.auth.jwt.Token;
import com.bisise.interviewserver.common.exception.EntityNotFoundException;
import com.bisise.interviewserver.common.exception.UnauthorizedException;
import com.bisise.interviewserver.common.message.ErrorMessage;
import com.bisise.interviewserver.domain.user.User;
import com.bisise.interviewserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.bisise.interviewserver.domain.user.User.createUser;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final JwtValidator jwtValidator;

    @Transactional(readOnly = true)
    public void splash(String email){
        getUser(email);
    }



    @Transactional
    public UserSignUpResponse signUp(UserSignUpRequest userSignUpRequest){
        User user = saveUser(userSignUpRequest);
        Token token = jwtProvider.issueToken(user.getEmail());
        updateRefreshToken(token, user);
        return UserSignUpResponse.of(token,user.getEmail());
    }

    @Transactional
    public UserSignInResponse signIn(UserSignInRequest userSignInRequest){
        User user = getUser(userSignInRequest.email());
        Token token = jwtProvider.issueToken(user.getEmail());
        updateRefreshToken(token,user);
        return UserSignInResponse.of(token,user.getEmail());
    }

    public void signOut(String email) {
        User findUser = getUser(email);
        deleteRefreshToken(findUser);
    }

    @Transactional(noRollbackFor = UnauthorizedException.class)
    public UserSignUpResponse reissue(String refreshToken, UserReissueRequest request) {
        String userEmail = request.email();
        validateRefreshToken(refreshToken, userEmail);
        User findUser = getUser(userEmail);
        Token issueToken = jwtProvider.issueToken(userEmail);
        updateRefreshToken(issueToken, findUser);
        return UserSignUpResponse.of(issueToken, findUser.getEmail());
    }



    private User getUser(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND));
    }



    private User saveUser(UserSignUpRequest userSignUpRequest){
        User user = createUser(userSignUpRequest.email());
        return userRepository.save(user);
    }

    private void updateRefreshToken(Token token, User user){
        user.updateRefreshToken(token.refreshToken());
    }

    private void deleteRefreshToken(User user){
        user.updateRefreshToken(null);
    }

    private void validateRefreshToken(String refreshToken, String userEmail) {
        try {
            jwtValidator.validateRefreshToken(refreshToken);
        } catch (UnauthorizedException e) {
            signOut(userEmail);
            throw e;
        }
    }
}
