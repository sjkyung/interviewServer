package com.bisise.interviewserver.api.user;


import com.bisise.interviewserver.api.user.dto.request.UserReissueRequest;
import com.bisise.interviewserver.api.user.dto.request.UserSignInRequest;
import com.bisise.interviewserver.api.user.dto.request.UserSignUpRequest;
import com.bisise.interviewserver.api.user.dto.response.UserSignInResponse;
import com.bisise.interviewserver.api.user.dto.response.UserSignUpResponse;
import com.bisise.interviewserver.api.user.service.UserService;
import com.bisise.interviewserver.common.ApiResponseUtil;
import com.bisise.interviewserver.common.BaseResponse;
import com.bisise.interviewserver.common.auth.UserId;
import com.bisise.interviewserver.common.message.SuccessMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/users")
@Tag(name = "User Controller", description = "회원 관련 API")
public class UserApiController {

    private final UserService userService;

  
    @Operation(summary = "유저 닉네임 중복 체크",
            description = "유저 닉네임 중복 체크")
    @GetMapping("/nick")
    public ResponseEntity<BaseResponse<?>> findNickname(String nickname){
        userService.findNickname(nickname);
        return ApiResponseUtil.success(SuccessMessage.OK);
    }

    @Operation(summary = "API 체크",
            description = "회원 API 체크 splash",
            security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("/splash")
    public ResponseEntity<BaseResponse<?>> splash(@Parameter(hidden = true) @UserId final Long userId){
        userService.splash(userId);
        return ApiResponseUtil.success(SuccessMessage.OK);
    }


    @Operation(
            summary = "회원 가입",
            description = "회원 가입 API access token 반환")
    @PostMapping("/signUp")
    public ResponseEntity<BaseResponse<?>> signUpUser(@RequestBody UserSignUpRequest request){
        UserSignUpResponse userSignUpResponse = userService.signUp(request);
        return ApiResponseUtil.success(SuccessMessage.CREATED,userSignUpResponse);
    }


    @Operation(
            summary = "회원 로그인",
            description = "회원 로그인 API access token, refresh token 반환")
    @PostMapping("/signIn")
    public ResponseEntity<BaseResponse<?>> signInUser(@RequestBody UserSignInRequest request){
        UserSignInResponse userSignInResponse = userService.signIn(request);
        return ApiResponseUtil.success(SuccessMessage.OK,userSignInResponse);
    }

    @Operation(
            security = @SecurityRequirement(name = "Authorization"),
            summary = "회원 로그아웃 ",
            description = "회원 로그아웃 API")
    @PatchMapping("/signOut")
    public ResponseEntity<BaseResponse<?>> signOut(@Parameter(hidden = true) @UserId final Long userId) {
        userService.signOut(userId);
        return ApiResponseUtil.success(SuccessMessage.OK);
    }

    @Operation(summary = "JWT 재발급 API",
            parameters = {
                    @Parameter(in = ParameterIn.HEADER, name = "X-Refresh-Token", required = true, description = "리프레시 토큰", schema = @Schema(type = "string"))
            })
    @PostMapping("/reissue")
    public ResponseEntity<BaseResponse<?>> reissue(@RequestHeader("X-Refresh-Token") final String refreshToken, @RequestBody final UserReissueRequest request) {
        final UserSignUpResponse response = userService.reissue(
                refreshToken,
                request);
        return ApiResponseUtil.success(SuccessMessage.OK, response);
    }

}
