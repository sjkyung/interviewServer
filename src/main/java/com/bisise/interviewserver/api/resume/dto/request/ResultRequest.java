package com.bisise.interviewserver.api.resume.dto.request;

import com.bisise.interviewserver.api.resume.dto.request.Result;

import java.util.List;

public record ResultRequest(Long userId, List<Result> results) {
}
