package com.beside.interviewserver.common.message;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessMessage {

    /**
     * 200 OK
     */
    OK(HttpStatus.OK,"S200","요청에 성공했습니다."),

    /**
     * 201 Created
      */
    CREATED(HttpStatus.CREATED,"S201","요청에 성공했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
