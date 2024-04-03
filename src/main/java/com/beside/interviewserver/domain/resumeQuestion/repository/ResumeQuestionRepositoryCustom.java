package com.beside.interviewserver.domain.resumeQuestion.repository;


import com.beside.interviewserver.domain.resumeQuestion.ResumeQuestion;
import com.beside.interviewserver.common.types.CategoryType;
import com.beside.interviewserver.common.types.ExperienceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResumeQuestionRepositoryCustom {
    Page<ResumeQuestion> getResumeQeustion(ExperienceType experienceType, CategoryType categoryType, Pageable pageable);
}
