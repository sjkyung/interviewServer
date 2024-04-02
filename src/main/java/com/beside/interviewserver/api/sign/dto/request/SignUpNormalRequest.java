package com.beside.interviewserver.api.sign.dto.request;

import com.beside.interviewserver.common.types.PlatformType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record SignUpNormalRequest(
        String nick,
        String careerExperience,
        String jobPosition,
        String platformId,
        @Enumerated(EnumType.STRING)
        PlatformType platform
) {
}
