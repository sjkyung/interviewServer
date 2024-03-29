package com.bisise.interviewserver.api.sign.dto.request;

import com.bisise.interviewserver.common.types.ExperienceType;

public record SignInRequest(
        String code,
        String loginProvider,
        ExperienceType experienceType
) {
}
