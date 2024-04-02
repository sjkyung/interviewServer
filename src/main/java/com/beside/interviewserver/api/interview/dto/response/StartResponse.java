package com.beside.interviewserver.api.interview.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record StartResponse(String answer) {
    public static StartResponse of(String prompt){
        return StartResponse.builder()
                .answer(prompt)
                .build();
    }
}
