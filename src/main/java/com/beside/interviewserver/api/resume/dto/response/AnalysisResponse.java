package com.beside.interviewserver.api.resume.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record AnalysisResponse(String answer) {
    public static AnalysisResponse of(String prompt){
        return AnalysisResponse.builder()
                .answer(prompt)
                .build();
    }
}
