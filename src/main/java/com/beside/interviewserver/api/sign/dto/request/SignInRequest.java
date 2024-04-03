package com.beside.interviewserver.api.sign.dto.request;

import com.beside.interviewserver.common.types.ExperienceType;

public record SignInRequest(
        String code,
        String loginProvider,
        ExperienceType experienceType
) {
}
