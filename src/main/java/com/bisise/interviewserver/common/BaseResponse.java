package com.bisise.interviewserver.common;

import com.bisise.interviewserver.common.message.ErrorMessage;
import com.bisise.interviewserver.common.message.SuccessMessage;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseResponse<T> {
    private final int status;
    private final String code;
    private final String message;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private final T data;


    public static BaseResponse<?> of(SuccessMessage successMessage){
        return BaseResponse.builder()
                .status(successMessage.getHttpStatus().value())
                .code(successMessage.getCode())
                .message(successMessage.getMessage())
                .build();
    }

    public static <T> BaseResponse<?> of(SuccessMessage successMessage, T data){
        return BaseResponse.builder()
                .status(successMessage.getHttpStatus().value())
                .code(successMessage.getCode())
                .message(successMessage.getMessage())
                .data(data)
                .build();
    }

    public static BaseResponse<?> of(ErrorMessage errorMessage){
        return BaseResponse.builder()
                .status(errorMessage.getHttpStatus().value())
                .code(errorMessage.getCode())
                .message(errorMessage.getMessage())
                .build();
    }
}
