package com.bisise.interviewserver.api.sign.dto.response.google;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record GoogleUserInfoResponse (
        String id,
        String email,
        @JsonProperty("verified_email")
        String verifiedEmail,
        String name,
        @JsonProperty("given_name")
        String givenName,
        @JsonProperty("family_name")
        String familyName,
        String picture,
        String locale
) {
}
