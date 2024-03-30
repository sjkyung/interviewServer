package com.bisise.interviewserver.external.openfeign.openai;

import java.util.Collections;
import java.util.List;

public record OpenAiResponse(List<Choice> choices) {
    //불변성 보장
    public OpenAiResponse(List<Choice> choices){
        this.choices = Collections.unmodifiableList(choices);
    }

    public record Choice(int index, Message message) {
    }
}
