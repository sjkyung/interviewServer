package com.bisise.interviewserver.common.exception;


import com.bisise.interviewserver.common.message.ErrorMessage;

public class ConflictException extends CommonException{
    public ConflictException() {
        super(ErrorMessage.CONFLICT);
    }

    public ConflictException(ErrorMessage errorMessage){
        super(errorMessage);
    }
}
