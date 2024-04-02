package com.bisise.interviewserver.common.message;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorMessage {
    /**
     * 400 BadRequest
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST,"E400","잘못된 요청입니다."),

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"E401","리소스 접근 권한이 없습니다."),

    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED,"E4011","액세스 토큰 형식이 올바르지 않습니다.Bearer 타입을 확인해주세요."),
    INVALID_ACCESS_TOKEN_VALUE(HttpStatus.UNAUTHORIZED,"E4012","액세스 토큰 값이 올바르지 않습니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED,"E4013","액세스 토큰 만료되었습니다. 재발급 받아주세요."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED,"E4014","리프레시 토큰 형식이 올바르지 않습니다.Bearer 타입을 확인해주세요."),
    INVALID_REFRESH_TOKEN_VALUE(HttpStatus.UNAUTHORIZED,"E4015","리프레시 토큰 값이 올바르지 않습니다"),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED,"E4016","리프레시 토큰 만료되었습니다.재발급 받아주세요."),
    MISMATCH_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED,"E4017","리프레시 토큰이 일치하지 않습니다."),

    FORBIDDEN(HttpStatus.FORBIDDEN,"E4030","리소스 접근 권한이 없습니다."),

    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "E4040", "대상을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "E4041", "존재하지 않는 회원입니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "E4042", "리프레쉬 토큰을 찾을 수 없습니다."),

    ALREADY_IN_USE(HttpStatus.CONFLICT, "E4043", "이미 사용중인 닉네임 입니다."),
    CONFLICT(HttpStatus.CONFLICT,"E4090","이미 존재하는 리소스 입니다."),
    DUPLICATE_USER(HttpStatus.CONFLICT,"E4091","이미 존재하는 회원입니다"),

    METHOD_NOT_FOUND(HttpStatus.METHOD_NOT_ALLOWED, "E4050", "잘못된 HTTP method 요청입니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"E500","서버 내부 오류입니다." );


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
