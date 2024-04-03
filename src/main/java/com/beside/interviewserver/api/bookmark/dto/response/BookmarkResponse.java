package com.beside.interviewserver.api.bookmark.dto.response;

import com.beside.interviewserver.domain.recruit.Recruit;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record BookmarkResponse (
        Long recruitId,
        String title,
        String company,
        String experienceRange,
        String deadline,
        String thumbnail,
        String positionId
){
    public static BookmarkResponse of(Recruit recruit){
        return BookmarkResponse.builder()
                .recruitId(recruit.getRecruitId())
                .title(recruit.getTitle())
                .company(recruit.getCompany())
                .experienceRange(recruit.getExperienceRange())
                .deadline(recruit.getDeadline())
                .thumbnail(recruit.getThumbnail())
                .positionId(recruit.getPositionId())
                .build();
    }
}
