package com.bisise.interviewserver.api.sign.service;


import com.bisise.interviewserver.api.sign.dto.request.SignInRequest;
import com.bisise.interviewserver.api.sign.dto.response.SignInResponse;

public interface SignService {
    public SignInResponse signIn (SignInRequest request);

}
