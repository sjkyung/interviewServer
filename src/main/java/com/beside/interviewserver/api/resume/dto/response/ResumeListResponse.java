package com.beside.interviewserver.api.resume.dto.response;


import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record ResumeListResponse(
                           Long interview_id,
                           String question,
                           String answer,
                           String ai_question
) {
    public static ResumeListResponse of(Long id, String question, String answer, String ai_question){
        return ResumeListResponse.builder()
                .interview_id(id)
                .question(question)
                .answer(answer)
                .ai_question(ai_question)
                .build();
    }
}
