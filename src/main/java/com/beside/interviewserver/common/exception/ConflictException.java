package com.beside.interviewserver.common.exception;


import com.beside.interviewserver.common.message.ErrorMessage;

public class ConflictException extends CommonException{
    public ConflictException() {
        super(ErrorMessage.CONFLICT);
    }

    public ConflictException(ErrorMessage errorMessage){
        super(errorMessage);
    }
}
