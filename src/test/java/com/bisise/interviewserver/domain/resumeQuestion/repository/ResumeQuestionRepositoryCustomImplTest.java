package com.bisise.interviewserver.domain.resumeQuestion.repository;

import com.bisise.interviewserver.domain.resumeQuestion.ResumeQuestion;
import com.bisise.interviewserver.common.types.CategoryType;
import com.bisise.interviewserver.common.types.ExperienceType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class ResumeQuestionRepositoryCustomImplTest {
    @Autowired
    private ResumeQuestionRepository repository;

    @Test
    @Transactional
    public void test () {
        Page<ResumeQuestion> resumeQeustion = repository.getResumeQeustion(ExperienceType.EXPERT, CategoryType.RECOMMEND, PageRequest.of(1, 4));
    }

}