package com.bisise.interviewserver.common.exception;

import com.bisise.interviewserver.common.message.ErrorMessage;

public class UnauthorizedException extends CommonException{

    public UnauthorizedException(){
        super(ErrorMessage.UNAUTHORIZED);
    }

    public UnauthorizedException(ErrorMessage errorMessage){
        super(errorMessage);
    }
}
