package com.beside.interviewserver.common.exception;

import com.beside.interviewserver.common.message.ErrorMessage;
import lombok.Getter;

@Getter
public class CommonException extends RuntimeException{
    private final ErrorMessage errorMessage;

    public CommonException(ErrorMessage errorMessage){
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

}
