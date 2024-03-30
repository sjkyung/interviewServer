package com.bisise.interviewserver.api.interview.service;

import com.bisise.interviewserver.api.interview.dto.request.QuestionRequest;
import com.bisise.interviewserver.api.interview.dto.request.InterviewResultRequest;
import com.bisise.interviewserver.api.interview.dto.request.StartRequest;
import com.bisise.interviewserver.api.interview.dto.response.ListResponse;
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

import java.util.List;


@Service
@RequiredArgsConstructor
public class InterviewService {
    private final InterviewRepository interviewRepository;
    private final OpenAiProvider openAiProvider;
    private final UserRepository userRepository;

    //질문 + 면접 = 결과 반환 서비스
    public StartResponse startResponse(StartRequest startRequest){

        String prompt = createAnswerPrompt(startRequest.question(), startRequest.answer());
        String openAiPrompt = openAiProvider.getOpenAiPrompt(prompt);
        return  StartResponse.of(openAiPrompt);
    }

    private String createAnswerPrompt(String question, String answer){
        String interviewStr = "\n면접관으로서 답변 분석해줘 합격,불합격 결과도 알려줘";
        String prefixedQuestion = "질문 : ".concat(question);
        String prefixedAnswer   = "답변 : ".concat(answer);
        return prefixedQuestion.concat(prefixedAnswer).concat(interviewStr);
    }

    //질문 생성 서비스
    public StartResponse questionResponse(QuestionRequest questionRequest){
        String prompt = createQuestionPrompt(questionRequest);
        String openAiPrompt = openAiProvider.getOpenAiPrompt(prompt);
        return StartResponse.of(openAiPrompt);
    }

    private String createQuestionPrompt(QuestionRequest questionRequest){
        return questionRequest.data().concat("\n 기업의 인재상인 회사의 면접질문 한가지 내줘");
    }


    public void result(InterviewResultRequest resultRequest){

        User user = userRepository.findById(resultRequest.userId()).orElseThrow(() ->
                new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND));

        resultRequest.results().stream().map(
                result -> {
                     Interview interview = Interview.createInterview(
                            user,
                            result.question(),
                            result.answer(),
                            result.pass()
                    );
                     return interview;
                }
        ).forEach(interviewRepository::save);
    }

    public List<ListResponse> list(Long userId){
        return interviewRepository.findByUserId(userId).stream().map(
                entity -> {
                    ListResponse listResponse = ListResponse.of(entity.getId(),
                            entity.getQuestion(),
                            entity.getAnswer(),
                            entity.getPass());
                    return listResponse;
                }
        ).toList();
    }
}
