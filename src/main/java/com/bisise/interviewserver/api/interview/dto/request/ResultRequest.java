package com.bisise.interviewserver.api.interview.dto.request;

import java.util.List;

public record ResultRequest(Long userId, List<Result> results) {
}
