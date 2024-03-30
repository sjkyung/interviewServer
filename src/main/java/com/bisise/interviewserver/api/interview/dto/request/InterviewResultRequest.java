package com.bisise.interviewserver.api.interview.dto.request;

import java.util.List;

public record InterviewResultRequest(Long userId, List<InterviewResult> results) {
}
