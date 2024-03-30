package com.bisise.interviewserver.api.bookmark.dto.request;

public record BookmarkRequest(
        Long userId,
        String title,
        String company,
        String experienceRange,
        String deadline,
        String thumbnail,
        String positionId
) {
}
