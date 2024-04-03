package com.beside.interviewserver.api.user.dto.request;

public record UserSignUpRequest(
        String email,
        String providerKey
) {
}
