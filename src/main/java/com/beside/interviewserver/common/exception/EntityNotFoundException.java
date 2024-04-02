package com.beside.interviewserver.common.exception;

import com.beside.interviewserver.common.message.ErrorMessage;
import lombok.Getter;

@Getter
public class EntityNotFoundException extends CommonException{
    public EntityNotFoundException(){
        super(ErrorMessage.ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException(ErrorMessage errorMessage){
        super(errorMessage);
    }
}
