package com.bisise.interviewserver.api.sign.dto.request;

import com.bisise.interviewserver.common.types.PlatformType;
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
