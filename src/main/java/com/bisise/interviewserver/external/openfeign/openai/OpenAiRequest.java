package com.bisise.interviewserver.external.openfeign.openai;

import lombok.AccessLevel;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record OpenAiRequest(String model, List<Message> messages) {
    public static OpenAiRequest of(String model, String prompt){
       return OpenAiRequest.builder()
                .model(model)
                .messages(new ArrayList<>(List.of(new Message("user",prompt))))
                .build();
    }
}
