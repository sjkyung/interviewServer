package com.beside.interviewserver.api.sign.service;


import com.beside.interviewserver.api.sign.dto.request.SignInRequest;
import com.beside.interviewserver.api.sign.dto.response.SignInResponse;

public interface SignService {
    public SignInResponse signIn (SignInRequest request);

}
