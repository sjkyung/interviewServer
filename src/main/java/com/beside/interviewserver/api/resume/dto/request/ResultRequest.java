package com.beside.interviewserver.api.resume.dto.request;

import com.beside.interviewserver.api.resume.dto.request.Result;

import java.util.List;

public record ResultRequest(List<Result> results) {
}
