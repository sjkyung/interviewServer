package com.beside.interviewserver.common;

import com.beside.interviewserver.common.BaseResponse;
import com.beside.interviewserver.common.message.ErrorMessage;
import com.beside.interviewserver.common.message.SuccessMessage;
import org.springframework.http.ResponseEntity;

import java.util.logging.ErrorManager;

public interface ApiResponseUtil {

    static ResponseEntity<BaseResponse<?>> success(SuccessMessage successMessage){
        return ResponseEntity.status(successMessage.getHttpStatus())
                .body(BaseResponse.of(successMessage));
    }

    static <T> ResponseEntity<BaseResponse<?>> success(SuccessMessage successMessage,T data){
        return ResponseEntity.status(successMessage.getHttpStatus())
                .body(BaseResponse.of(successMessage,data));
    }

    static ResponseEntity<BaseResponse<?>> failure(ErrorMessage errorMessage){
        return ResponseEntity.status(errorMessage.getHttpStatus())
                .body(BaseResponse.of(errorMessage));
    }
}
