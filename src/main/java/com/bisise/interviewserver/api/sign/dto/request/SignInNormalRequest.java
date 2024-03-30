package com.bisise.interviewserver.api.sign.dto.request;

import com.bisise.interviewserver.common.types.PlatformType;

public record SignInNormalRequest(
        PlatformType platform,
        String platformId
) {
}
