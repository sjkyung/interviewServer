package com.bisise.interviewserver.domain.resumeQuestion.repository;


import com.bisise.interviewserver.domain.resumeQuestion.ResumeQuestion;
import com.bisise.interviewserver.common.types.CategoryType;
import com.bisise.interviewserver.common.types.ExperienceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResumeQuestionRepositoryCustom {
    Page<ResumeQuestion> getResumeQeustion(ExperienceType experienceType, CategoryType categoryType, Pageable pageable);
}
