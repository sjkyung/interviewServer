package com.beside.interviewserver.domain.resumeQuestion.repository;

import com.beside.interviewserver.domain.recruit.Recruit;
import com.beside.interviewserver.domain.resumeQuestion.ResumeQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeQuestionRepository extends JpaRepository<ResumeQuestion,Long>, ResumeQuestionRepositoryCustom {
}
