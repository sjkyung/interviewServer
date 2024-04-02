package com.beside.interviewserver.external.openfeign.openai;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "openaiFeignClient", url = "${openai.url}")
public interface OpenAiFeignClient {

    @PostMapping
    OpenAiResponse getOpenAiPromptInfo(@RequestHeader("Authorization") String apikey, @RequestBody OpenAiRequest request);

}
