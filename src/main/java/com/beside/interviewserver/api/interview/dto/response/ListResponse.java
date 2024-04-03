package com.beside.interviewserver.api.interview.dto.response;


import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record ListResponse(
                           Long interview_id,
                           String question,
                           String answer,
                           String pass
) {
    public static ListResponse of(Long id, String question, String answer, String pass){
        return ListResponse.builder()
                .interview_id(id)
                .question(question)
                .answer(answer)
                .pass(pass)
                .build();
    }
}
