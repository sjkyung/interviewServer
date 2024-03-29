package com.bisise.interviewserver.api.interview.service;

import com.bisise.interviewserver.api.interview.dto.request.QuestionRequest;
import com.bisise.interviewserver.api.interview.dto.request.ResultRequest;
import com.bisise.interviewserver.api.interview.dto.request.StartRequest;
import com.bisise.interviewserver.api.interview.dto.response.StartResponse;
import com.bisise.interviewserver.common.exception.EntityNotFoundException;
import com.bisise.interviewserver.common.message.ErrorMessage;
import com.bisise.interviewserver.domain.interview.Interview;
import com.bisise.interviewserver.domain.interview.repository.InterviewRepository;
import com.bisise.interviewserver.domain.user.User;
import com.bisise.interviewserver.domain.user.repository.UserRepository;
import com.bisise.interviewserver.external.openfeign.openai.OpenAiProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class InterviewService {
    private final InterviewRepository interviewRepository;
    private final OpenAiProvider openAiProvider;
    private final UserRepository userRepository;

    //질문 + 면접 = 결과 반환 서비스
    public StartResponse startResponse(StartRequest startRequest){

        String prompt = createPrompt(startRequest.question(), startRequest.answer());
        String openAiPrompt = openAiProvider.getOpenAiPrompt(prompt);
        return  StartResponse.of(openAiPrompt);
    }

    private String createPrompt(String question, String answer){
        String interviewStr = "\n면접관으로서 답변 분석해줘";
        String prefixedQuestion = "질문 : ".concat(question);
        String prefixedAnswer   = "답변 : ".concat(answer);
        return prefixedQuestion.concat(prefixedAnswer).concat(interviewStr);
    }

    //질문 생성 서비스
    public StartResponse questionResponse(QuestionRequest questionRequest){
        String prompt = createPrompt(questionRequest);
        String openAiPrompt = openAiProvider.getOpenAiPrompt(prompt);
        return StartResponse.of(openAiPrompt);
    }

    private String createPrompt(QuestionRequest questionRequest){
        return questionRequest.data().concat("\n면접 질문 내줘");
    }


    public void result(ResultRequest resultRequest){
        User user = userRepository.findById(resultRequest.userId()).orElseThrow(() ->
                new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND));

        resultRequest.results().stream().map(
                result -> {
                     Interview interview = Interview.createInterview(
                            user,
                            result.question(),
                            result.answer()
                    );
                     return interview;
                }
        ).forEach(interviewRepository::save);

    }
}
