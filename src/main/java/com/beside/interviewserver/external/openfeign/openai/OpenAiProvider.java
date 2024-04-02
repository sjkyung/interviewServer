package com.beside.interviewserver.external.openfeign.openai;

import com.beside.interviewserver.common.exception.UnauthorizedException;
import com.beside.interviewserver.common.message.ErrorMessage;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class OpenAiProvider {
    private final OpenAiFeignClient openAiFeignClient;
    @Value("${openai.model}")
    private String model;
    @Value("${openai.apikey}")
    private String apikey;

    public String getOpenAiPrompt(String prompt){
        String openAiApikey = OpenAiKey.createOpenAiKey(apikey);
        OpenAiRequest openAiRequest = OpenAiRequest.of(model,prompt);
        OpenAiResponse openAiResponse = getOpenAiResponse(openAiApikey,openAiRequest);
        return openAiResponse.choices()
                .stream()
                .map(choice -> choice.message().content()
        ).collect(Collectors.joining());
    }

    private OpenAiResponse getOpenAiResponse(String apikey, OpenAiRequest request){
        try {
            return openAiFeignClient.getOpenAiPromptInfo(apikey,request);
        }catch (FeignException e){
            log.info("OpenAi API Exception",e);
            throw new UnauthorizedException(ErrorMessage.UNAUTHORIZED);
        }
    }
}
