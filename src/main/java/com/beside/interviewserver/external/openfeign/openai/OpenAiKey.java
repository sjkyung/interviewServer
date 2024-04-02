package com.beside.interviewserver.external.openfeign.openai;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OpenAiKey {
    private String apikey;

    public static String createOpenAiKey(String apikey){
        return "Bearer ".concat(apikey);
    }
}
