package com.beside.interviewserver.api.resume.service;


import com.beside.interviewserver.api.interview.dto.request.StartRequest;
import com.beside.interviewserver.api.resume.dto.request.ResultRequest;
import com.beside.interviewserver.api.resume.dto.response.AnalysisResponse;
import com.beside.interviewserver.api.resume.dto.response.ResumeListResponse;
import com.beside.interviewserver.common.exception.EntityNotFoundException;
import com.beside.interviewserver.common.message.ErrorMessage;
import com.beside.interviewserver.domain.resume.Resume;
import com.beside.interviewserver.domain.resume.repository.ResumeRepository;
import com.beside.interviewserver.domain.user.User;
import com.beside.interviewserver.domain.user.repository.UserRepository;
import com.beside.interviewserver.external.openfeign.openai.OpenAiProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final OpenAiProvider openAiProvider;


    //질문 + 면접 = 결과 반환 서비스
    public AnalysisResponse analysisResponse(StartRequest startRequest){
        String prompt = createPrompt(startRequest.question(), startRequest.answer());
        String openAiPrompt = openAiProvider.getOpenAiPrompt(prompt);
        return  AnalysisResponse.of(openAiPrompt);
    }

    private String createPrompt(String question, String answer){
        String interviewStr = "\n세가지 답변으로 자소서 분석해줘 ";
        String prefixedQuestion = "질문 : ".concat(question);
        String prefixedAnswer   = "답변 : ".concat(answer);
        return prefixedQuestion.concat(prefixedAnswer).concat(interviewStr);
    }

    @Transactional
    public void result(Long userId, ResultRequest resultRequest){
        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND));

        resultRequest.results().stream().map(
                result -> {
                    Resume resume = Resume.createResume(
                            user,
                            result.question(),
                            result.answer(),
                            result.ai_answer()
                    );
                    return resume;
                }
        ).forEach(resumeRepository::save);
    }

    public List<ResumeListResponse> list(Long userId){
        return resumeRepository.findByUserId(userId).stream().map(
                entity -> {
                    ResumeListResponse listResponse = ResumeListResponse.of(entity.getId(),
                            entity.getQuestion(),
                            entity.getAnswer(),
                            entity.getAi_answer());
                    return listResponse;
                }
        ).toList();
    }
}
