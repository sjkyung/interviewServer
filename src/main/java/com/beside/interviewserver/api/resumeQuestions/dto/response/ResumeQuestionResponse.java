package com.beside.interviewserver.api.resumeQuestions.dto.response;

import com.beside.interviewserver.domain.resumeQuestion.ResumeQuestion;
import com.beside.interviewserver.common.types.CategoryType;
import com.beside.interviewserver.common.types.ExperienceType;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record ResumeQuestionResponse (
    Long resumeQuestionId,
    String title,
    String subTitle,
    ExperienceType experienceRange,
    CategoryType category
){
    public static ResumeQuestionResponse of(ResumeQuestion resumeQuestion) {
        return ResumeQuestionResponse.builder()
                .resumeQuestionId(resumeQuestion.getResumeQuestionsId())
                .title(resumeQuestion.getTitle())
                .subTitle(resumeQuestion.getSubTitle())
                .experienceRange(resumeQuestion.getExperienceRange())
                .category(resumeQuestion.getCategory())
                .build();
    }
}
