package com.bisise.interviewserver.common.exception;

import com.bisise.interviewserver.common.message.ErrorMessage;
import lombok.Getter;

@Getter
public class CommonException extends RuntimeException{
    private final ErrorMessage errorMessage;

    public CommonException(ErrorMessage errorMessage){
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

}
