package com.beside.interviewserver.api.resumeQuestions.service;

import com.beside.interviewserver.api.bookmark.dto.request.BookmarkRequest;
import com.beside.interviewserver.api.resumeQuestions.dto.request.ResumeQuestioRequest;
import com.beside.interviewserver.api.resumeQuestions.dto.response.ResumeQuestionResponse;
import com.beside.interviewserver.common.dto.PageableRequest;
import com.beside.interviewserver.domain.resumeQuestion.ResumeQuestion;
import com.beside.interviewserver.domain.resumeQuestion.repository.ResumeQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumeQuestionService {
    private final ResumeQuestionRepository resumeQuestionRepository;
    public Page<ResumeQuestionResponse> getResumeQuestions(ResumeQuestioRequest resumeQuestioRequest, PageableRequest pageableRequest) {
        return resumeQuestionRepository.getResumeQeustion(
                resumeQuestioRequest.experienceType(),
                resumeQuestioRequest.categoryType(),
                pageableRequest.toPageable()).map(ResumeQuestionResponse::of);
    }
}
