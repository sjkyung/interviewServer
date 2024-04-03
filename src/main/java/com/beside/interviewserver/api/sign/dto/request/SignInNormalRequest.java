package com.beside.interviewserver.api.sign.dto.request;

import com.beside.interviewserver.common.types.PlatformType;

public record SignInNormalRequest(
        PlatformType platform,
        String platformId
) {
}
