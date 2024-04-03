package com.beside.interviewserver.domain.resumeQuestion.repository;

import com.beside.interviewserver.domain.resumeQuestion.QResumeQuestion;
import com.beside.interviewserver.domain.resumeQuestion.ResumeQuestion;
import com.beside.interviewserver.common.types.CategoryType;
import com.beside.interviewserver.common.types.ExperienceType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResumeQuestionRepositoryCustomImpl implements ResumeQuestionRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;
    private static final QResumeQuestion resumeQuestion = QResumeQuestion.resumeQuestion;

    @Override
    public Page<ResumeQuestion> getResumeQeustion(ExperienceType experienceType, CategoryType categoryType, Pageable pageable) {
        List<ResumeQuestion> contents = jpaQueryFactory.select(resumeQuestion)
                .from(resumeQuestion)
                .where(resumeQuestion.experienceRange.eq(experienceType)
                        .and(resumeQuestion.category.eq(categoryType)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        Long totalCount = jpaQueryFactory.select(resumeQuestion.resumeQuestionsId)
                .from(resumeQuestion)
                .where(resumeQuestion.experienceRange.eq(experienceType)
                        .and(resumeQuestion.category.eq(categoryType)))
                .stream().count();
        return new PageImpl<>(contents, pageable, totalCount);
    }
}
