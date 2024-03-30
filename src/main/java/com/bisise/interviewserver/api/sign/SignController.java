package com.bisise.interviewserver.api.sign;


import com.bisise.interviewserver.api.sign.dto.request.SignInRequest;
import com.bisise.interviewserver.api.sign.dto.request.SignInNormalRequest;
import com.bisise.interviewserver.api.sign.dto.request.SignUpNormalRequest;
import com.bisise.interviewserver.api.sign.dto.response.SignInResponse;
import com.bisise.interviewserver.api.sign.dto.response.SignNormalResponse;
import com.bisise.interviewserver.api.sign.service.SignService;
import com.bisise.interviewserver.api.sign.service.impl.SignInNormalService;
import com.bisise.interviewserver.common.ApiResponseUtil;
import com.bisise.interviewserver.common.BaseResponse;
import com.bisise.interviewserver.common.message.SuccessMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
@Tag(name = "SignController", description = "쇼셜 로그인 API")
public class SignController {

    private final SignService signInKakaoService;
    private final SignService signInGoogleService;
    private final SignService signInNaverService;
    private final SignInNormalService signInNormalService;
    @Operation(summary = "카카오 로그인",
            description = "카카오 로그인 API Token & User Info 반환")
    @PostMapping("/sign-in/kakao")
    public ResponseEntity<BaseResponse<?>> signInKakao(@RequestBody SignInRequest request) throws JsonProcessingException {
        SignInResponse signInResponse = signInKakaoService.signIn(request);
        return ApiResponseUtil.success(SuccessMessage.OK,signInResponse);
    }

    @Operation(summary = "구글 로그인",
            description = "구글 로그인 API Token & User Info 반환")
    @PostMapping("/sign-in/google")
    public ResponseEntity<BaseResponse<?>> signInGoogle(@RequestBody SignInRequest request) throws JsonProcessingException {
        SignInResponse signInResponse = signInGoogleService.signIn(request);
        return ApiResponseUtil.success(SuccessMessage.OK,signInResponse);
    }

    @Operation(summary = "네이버 로그인",
            description = "네이버 로그인 API Token & User Info 반환")
    @PostMapping("/sign-in/naver")
    public ResponseEntity<BaseResponse<?>> signInNaver(@RequestBody SignInRequest request) throws JsonProcessingException {
        SignInResponse signInResponse = signInNaverService.signIn(request);
        return ApiResponseUtil.success(SuccessMessage.OK,signInResponse);
    }

    @Operation(summary = "유저 로그인 정보 조회",
            description = "유저 로그인 정보 조회")
    @PostMapping("/sign-in")
    public ResponseEntity<BaseResponse<?>> signIn(SignInNormalRequest request) {
        SignNormalResponse signUpNormalResponse = signInNormalService.signIn(request);
        return ApiResponseUtil.success(SuccessMessage.OK,signUpNormalResponse);
    }
    @Operation(summary = "유저 회원 가입",
            description = "유저 회원 가입")
    @PostMapping("/sign-up")
    public ResponseEntity<BaseResponse<?>> signIn(SignUpNormalRequest request) {
        SignNormalResponse signUpNormalResponse = signInNormalService.signUp(request);
        return ApiResponseUtil.success(SuccessMessage.OK, signUpNormalResponse);
    }
}
